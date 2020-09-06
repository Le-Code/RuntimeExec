package com.company;

import java.util.List;

public interface Operate {

    List<String> copy(String fromPth, String toPth);

    List<String> delete(String path);
}
