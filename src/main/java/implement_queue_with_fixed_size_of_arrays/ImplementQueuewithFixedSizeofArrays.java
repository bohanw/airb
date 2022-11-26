package implement_queue_with_fixed_size_of_arrays;

import java.util.*;

import org.junit.*;

import static org.junit.Assert.*;

public class ImplementQueuewithFixedSizeofArrays {
    /*
        Implement Queue with Fixed Size of Arrays
        AirBnB Interview Question

        Use Array :
        public class QueueWithFixedArray {
    private int fixedSize;
    private int cnt;
    private int head;
    private int tail;
    private Object[] headArr;
    private Object[] tailArr;

    public QueueWithFixedArray(int fixedSize) {
      this.fixedSize = fixedSize;
      this.cnt = 0;
      this.head = 0;
      this.tail = 0;
      this.headArr = new Object[fixedSize];
      this.tailArr = headArr;
    }

    public void offer(int num) {
      if (tail == fixedSize - 1) {
        Object[] newArr = new Object[fixedSize];
        newArr[0] = num;
        tailArr[fixedSize - 1] = newArr;
        tailArr = newArr;
        tail = 0;
      } else {
        tailArr[tail] = num;
      }
      tail++;
      cnt++;
    }

    public int poll() {
      if (cnt == 0) return -1;
      int num = (int) headArr[head];
      head++;
      cnt--;

      if (head == fixedSize - 1) {
        headArr = (Object[]) headArr[fixedSize - 1];
        head = 0;
      }
      return num;
    }

    public int size() {
      return cnt;
    }
  }

  Thoughts:
  Usw fixedSize list/array, wehre the last index of each fixed size list points to
  the next fixed-size list
  maintain head/tail pointers

     */

    public class QueueWithFixedArray {
        private int fixedSize; //Given size of the array

        private int count;
        private int head;
        private int tail;
        private List<Object> headList;
        private List<Object> tailList;

        public QueueWithFixedArray(int fixedSize) {
            this.fixedSize = fixedSize;
            this.count = 0;
            this.head = 0;
            this.tail = 0;
            this.headList = new ArrayList<>();
            this.tailList = this.headList;
        }

        public void offer(int num) {
            if (tail == fixedSize - 1) {
                List<Object> newList = new ArrayList<>();
                newList.add(num);
                tailList.add(newList);
                tailList = (List<Object>) tailList.get(tail);
                tail = 0;
            } else {
                tailList.add(num);
            }
            count++;
            tail++;
        }

        public Integer poll() {
            if (count == 0) {
                return null;
            }

            int num = (int) headList.get(head);
            head++;
            count--;

            if (head == fixedSize - 1) {
                List<Object> newList = (List<Object>) headList.get(head);
                headList.clear();
                headList = newList;
                head = 0;
            }

            return num;
        }

        public int size() {
            return count;
        }
    }

    public static class UnitTest {
        @Test
        public void test1() {
            QueueWithFixedArray queue = new ImplementQueuewithFixedSizeofArrays().new QueueWithFixedArray(5);
            queue.offer(1);
            queue.offer(2);
            int res = queue.poll();
            assertEquals(2, res);
            queue.offer(3);
            queue.offer(4);
            queue.offer(5);
            queue.offer(6);
            queue.offer(7);
            queue.offer(8);
            queue.offer(9);
            res = queue.poll();
            assertEquals(2, res);
            res = queue.poll();
            System.out.println("Bohan :  res "  + res);
            assertEquals(3, res);
        }
    }
}

