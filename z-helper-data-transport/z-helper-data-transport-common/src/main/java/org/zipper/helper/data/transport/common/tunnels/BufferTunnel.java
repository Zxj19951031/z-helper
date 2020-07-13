package org.zipper.helper.data.transport.common.tunnels;

import cn.hutool.core.lang.Assert;
import org.zipper.helper.data.transport.common.commons.CoreConstant;
import org.zipper.helper.data.transport.common.errors.CommonError;
import org.zipper.helper.data.transport.common.records.Record;
import org.zipper.helper.data.transport.common.records.SkipRecord;
import org.zipper.helper.data.transport.common.records.TerminateRecord;
import org.zipper.helper.exception.HelperException;
import org.zipper.helper.util.json.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BufferTunnel implements RecordConsumer, RecordProducer {

    private final BufferQueue queue;
    private int bufferSize;
    protected final int byteCapacity;
    private final List<Record> buffer;
    private int bufferIndex = 0;
    private volatile boolean shutdown = false;
    private final AtomicInteger memoryBytes = new AtomicInteger(0);

    public BufferTunnel(JsonObject tunnelConfig) {
        assert null != tunnelConfig;
        this.bufferSize = tunnelConfig.getInt(CoreConstant.TUNNEL_BUFFER_SIZE, 1000);
        this.byteCapacity = tunnelConfig.getInt(CoreConstant.TUNNEL_BYTE_CAPACITY, 8 * 1024 * 1024);
        this.buffer = new ArrayList<>(this.bufferSize);
        this.queue = new BufferQueue(this.bufferSize);
    }

    @Override
    public Record consume() {
        if (shutdown) {
            throw HelperException.newException(CommonError.SHUT_DOWN_TASK, "");
        }

        boolean isEmpty = (this.bufferIndex >= this.buffer.size());
        if (isEmpty) {
            this.queue.doPullAll(this.buffer);
            this.bufferIndex = 0;
            this.bufferSize = this.buffer.size();
        }

        Record record = this.buffer.get(this.bufferIndex++);
        if (record instanceof TerminateRecord) {
            record = null;
        }
        return record;

    }

    @Override
    public void produce() {
        this.produce(SkipRecord.get(), true);
    }

    @Override
    public void produce(Record record) {
        this.produce(record, false);
    }

    @Override
    public void produce(Record record, boolean immediately) {
        if (shutdown) {
            throw HelperException.newException(CommonError.SHUT_DOWN_TASK, "");
        }

        Assert.notNull(record, "record不能为空.");

        if (record.getMemorySize() > this.byteCapacity) {

            // TODO: 2020/2/14 单条记录的容量控制
//            this.pluginCollector.collectDirtyRecord(record, new Exception(String.format("单条记录超过大小限制，当前限制为:%s", this.byteCapacity)));
            return;
        }

        boolean isFull = (this.bufferIndex >= this.bufferSize || this.memoryBytes.get() + record.getMemorySize() > this.byteCapacity);
        if (isFull || immediately) {
            flush();
        }

        this.buffer.add(record);
        this.bufferIndex++;
        memoryBytes.addAndGet(record.getMemorySize());
    }

    public void flush() {
        if (shutdown) {
            throw HelperException.newException(CommonError.SHUT_DOWN_TASK, "");
        }
        this.queue.doPushAll(this.buffer);
        this.buffer.clear();
        this.bufferIndex = 0;
        this.memoryBytes.set(0);
    }

    public void terminate() {
        if (shutdown) {
            throw HelperException.newException(CommonError.SHUT_DOWN_TASK, "");
        }
        flush();
        this.queue.terminate();
    }

    public void shutdown() {
        shutdown = true;
        try {
            buffer.clear();
            queue.clear();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


}
