package org.zipper.helper.data.transport.common.tunnels;


import org.zipper.helper.data.transport.common.records.Record;

public interface RecordConsumer {
    Record consume();
}
