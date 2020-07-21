package org.zipper.helper.data.transport.common.records;


import org.zipper.helper.data.transport.common.columns.Column;

public interface Record {
    void addColumn(Column column);

    void setColumn(int i, final Column column);

    Column getColumn(int i);

    @Override
    String toString();

    int getColumnNumber();

    int getByteSize();

    int getMemorySize();
}
