package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    static class Event implements Comparable {
        int id;
        String value;
        public Event(int id, String value) {
            this.id = id;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Event{" +
                    "id=" + id +
                    ", value='" + value + '\'' +
                    '}';
        }

        @Override
        public int compareTo(Object o) {
            if (o instanceof Event) {
                Event obj = (Event) o;
                return this.id - obj.id;
            }
            return 0;
        }
    }

    public static void main(String[] args) {
	// write your code here
        SubQueue<Event> subQueue = new SubQueue<>();
        Random random = new Random();
        List<Event> contents = new ArrayList<>();
        int removeNum = 0;
        for (int i = 0; i < 10; i++) {
            removeNum = random.nextInt(10);
            Event event = new Event(random.nextInt(10), "value" + removeNum);
            contents.add(event);
            subQueue.add(event);
        }
        System.out.println("开始排序 " + subQueue.size());
        while (!subQueue.isEmpty()) {
            System.out.println(subQueue.poll());
        }
        for (Event c : contents) {
            subQueue.add(c);
        }
        System.out.println();
        System.out.println("删除元素：" + removeNum);
        int finalRemoveNum1 = removeNum;
        subQueue.remove(new SubQueue.IFilter<Event>() {
            @Override
            public boolean match(Event obj) {
                return obj.value.equals(("value" + finalRemoveNum1));
            }
        });
        System.out.println("开始排序 " + subQueue.size());
        while (!subQueue.isEmpty()) {
            System.out.println(subQueue.poll());
        }

        System.out.println("开始插入");
        for (Event c : contents) {
            subQueue.add(c);
        }
        subQueue.remove(new SubQueue.IFilter<Event>() {
            @Override
            public boolean match(Event obj) {
                return obj.value.equals(("value" + finalRemoveNum1));
            }
        });
        for (Event c : contents) {
            subQueue.add(c);
        }
        System.out.println("开始排序 " + subQueue.size());
        while (!subQueue.isEmpty()) {
            System.out.println(subQueue.poll());
        }
    }
}
