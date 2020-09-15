package com.company;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

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

    private static void testFindPerformance() {
        long subQueueTime = 0;
        long priQueueTime = 0;
        SubQueue<Integer> subQueue = new SubQueue<>();
        PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<>();
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            subQueue.clear();
            priorityBlockingQueue.clear();
            for (int j = 0; j < 1000; j++) {
                int id = random.nextInt(1000);
                subQueue.add(id);
                priorityBlockingQueue.add(id);
            }
            int removeId = random.nextInt(100);
            long st = System.nanoTime();
            subQueue.remove(new SubQueue.IFilter<Integer>() {
                @Override
                public boolean match(Integer obj) {
                    return obj == removeId;
                }
            });
            subQueueTime += (System.nanoTime() - st);

            long st2 = System.nanoTime();
            priorityBlockingQueue.contains(removeId);
            priQueueTime += (System.nanoTime() - st2);
        }

        System.out.println("subQueue remove item consume time : " + subQueueTime * 1.0 / 10000);
        System.out.println("PriorityBlockingQueue remove item consume time : " + priQueueTime * 1.0 / 10000);
    }

    private static void testFindPerformance2() {
        long subQueueTime = 0;
        long priQueueTime = 0;
        SubQueue<Event> subQueue = new SubQueue<>();
        PriorityBlockingQueue<Event> priorityBlockingQueue = new PriorityBlockingQueue<>();
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            subQueue.clear();
            priorityBlockingQueue.clear();
            for (int j = 0; j < 100; j++) {
                subQueue.add(new Event(j, "value" + j));
                priorityBlockingQueue.add(new Event(j, "value" + j));
            }
            int removeId = random.nextInt(100);
            long st = System.nanoTime();
            subQueue.find(new SubQueue.IFilter<Event>() {
                @Override
                public boolean match(Event obj) {
                    return obj.id == removeId;
                }
            });
            subQueueTime += (System.nanoTime() - st);

            priQueueTime += priFind(priorityBlockingQueue, removeId);
        }

        System.out.println("subQueue remove item consume time : " + subQueueTime * 1.0 / 10000);
        System.out.println("PriorityBlockingQueue remove item consume time : " + priQueueTime * 1.0 / 10000);
    }

    private static long priFind(PriorityBlockingQueue<Event> queue, int key) {
        long st = System.nanoTime();
        Iterator<Event> iterator = queue.iterator();
        while (iterator.hasNext()) {
            Event item = iterator.next();
            if (item.id == key) {
                break;
            }
        }
        long total = System.nanoTime() - st;
        return total;
    }

    private static void testRemovePerformance() {
        long subQueueTime = 0;
        long priQueueTime = 0;
        SubQueue<Integer> subQueue = new SubQueue<>();
        PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<>();
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            subQueue.clear();
            priorityBlockingQueue.clear();
            for (int j = 0; j < 1000; j++) {
                int id = random.nextInt(1000);
                subQueue.add(id);
                priorityBlockingQueue.add(id);
            }
            int removeId = random.nextInt(100);
            long st = System.nanoTime();
            subQueue.remove(new SubQueue.IFilter<Integer>() {
                @Override
                public boolean match(Integer obj) {
                    return obj == removeId;
                }
            });
            subQueueTime += (System.nanoTime() - st);

            priQueueTime += priRemove(priorityBlockingQueue, removeId);
        }

        System.out.println("subQueue remove item consume time : " + subQueueTime * 1.0 / 10000);
        System.out.println("PriorityBlockingQueue remove item consume time : " + priQueueTime * 1.0 / 10000);
    }

    private static long priRemove(PriorityBlockingQueue<Integer> queue, int key) {
        long st = System.nanoTime();
        Iterator<Integer> iterator = queue.iterator();
        while (iterator.hasNext()) {
            Integer item = iterator.next();
            if (item == key) {
                iterator.remove();
            }
        }
        long total = System.nanoTime() - st;
        return total;
    }

    public static void main(String[] args) {
	// write your code here
        /*SubQueue<Event> subQueue = new SubQueue<>();
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
        }*/

        // compare performance
//        testRemovePerformance();
        testFindPerformance2();
    }
}
