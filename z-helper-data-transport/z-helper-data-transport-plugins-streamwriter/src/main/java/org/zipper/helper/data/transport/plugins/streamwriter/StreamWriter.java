package org.zipper.helper.data.transport.plugins.streamwriter;

import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zipper.helper.data.transport.common.Writer;
import org.zipper.helper.data.transport.common.records.Record;
import org.zipper.helper.data.transport.common.tunnels.RecordConsumer;
import org.zipper.helper.util.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class StreamWriter extends Writer {

    public static class Job extends Writer.Job {
        private static final Logger log = LoggerFactory.getLogger(StreamWriter.Job.class);

        @Override
        public List<JsonObject> split(int channel) {
            return new ArrayList<JsonObject>() {{
                add(getAllConfig());
            }};
        }

        @Override
        public void init() {

        }

        @Override
        public void destroy() {

        }
    }

    public static class Task extends Writer.Task {
        private static final Logger log = LoggerFactory.getLogger(StreamWriter.Task.class);

        @Override
        public void startWrite(RecordConsumer consumer) {
            Record record = null;
            while ((record = consumer.consume()) != null) {
                log.info(JSONUtil.parse(record).toJSONString(2));
            }
        }

        @Override
        public void init() {

        }

        @Override
        public void destroy() {

        }
    }
}
