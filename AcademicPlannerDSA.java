import java.util.*;

/* Assignment class */

class Assignment {

    String title;
    String course;
    String dueDate;  
    int progress;

    Assignment(String t, String c, String d, int p) {
        title = t;
        course = c;
        dueDate = d;
        progress = p;
    }
}

/* ========================= */
/* CO2: Doubly Linked List   */
/* ========================= */

class Node {

    Assignment data;
    Node prev;
    Node next;

    Node(Assignment a) {
        data = a;
        prev = null;
        next = null;
    }
}

class DoublyLinkedList {

    Node head;

    void insert(Assignment a) {

        Node newNode = new Node(a);

        if (head == null) {
            head = newNode;
            return;
        }

        Node temp = head;

        while (temp.next != null)
            temp = temp.next;

        temp.next = newNode;
        newNode.prev = temp;
    }

    void display() {

        Node temp = head;

        while (temp != null) {

            System.out.println("Title: " + temp.data.title);
            System.out.println("Course: " + temp.data.course);
            System.out.println("Due Date: " + temp.data.dueDate);
            System.out.println("Progress: " + temp.data.progress + "%");
            System.out.println("---------------------");

            temp = temp.next;
        }
    }
}

/* ================================= */
/* CO1: Searching and Sorting        */
/* ================================= */

class SearchSort {

    static void search(Node head, String title) {

        Node temp = head;

        while (temp != null) {

            if (temp.data.title.equals(title)) {

                System.out.println("Assignment Found!");
                System.out.println("Course: " + temp.data.course);
                System.out.println("Due Date: " + temp.data.dueDate);
                return;
            }

            temp = temp.next;
        }

        System.out.println("Assignment Not Found");
    }

    /* Convert DD/MM/YYYY to sortable number */

    static int convertDate(String date) {

        String parts[] = date.split("/");

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        return year * 10000 + month * 100 + day;
    }

    /* Bubble sort based on date */

    static void sort(Node head) {

        Node i, j;

        for (i = head; i != null; i = i.next) {

            for (j = i.next; j != null; j = j.next) {

                int d1 = convertDate(i.data.dueDate);
                int d2 = convertDate(j.data.dueDate);

                if (d1 > d2) {

                    Assignment temp = i.data;
                    i.data = j.data;
                    j.data = temp;
                }
            }
        }
    }
}

/* ========================= */
/* CO3: Stack & Queue        */
/* ========================= */

class StackQueueManager {

    Stack<Assignment> undoStack = new Stack<>();
    Queue<Assignment> reminderQueue = new LinkedList<>();

    void pushUndo(Assignment a) {
        undoStack.push(a);
    }

    void undoLast() {

        if (undoStack.isEmpty()) {
            System.out.println("Nothing to Undo");
            return;
        }

        Assignment a = undoStack.pop();

        System.out.println("Undo Assignment: " + a.title);
    }

    void addReminder(Assignment a) {
        reminderQueue.add(a);
    }

    void processReminders() {

        while (!reminderQueue.isEmpty()) {

            Assignment a = reminderQueue.remove();

            System.out.println("Reminder: " + a.title + " due on " + a.dueDate);
        }
    }
}

/* ========================= */
/* CO4: Hash Table           */
/* ========================= */

class HashTableManager {

    HashMap<String, Assignment> map = new HashMap<>();

    void insert(Assignment a) {
        map.put(a.title, a);
    }

    void search(String title) {

        if (map.containsKey(title)) {

            Assignment a = map.get(title);

            System.out.println("Found in HashTable");
            System.out.println("Course: " + a.course);
            System.out.println("Due Date: " + a.dueDate);
        }
        else {
            System.out.println("Assignment not found");
        }
    }
}

/* ========================= */
/* Main Program              */
/* ========================= */

public class AcademicPlannerDSA {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        DoublyLinkedList list = new DoublyLinkedList();
        StackQueueManager sqm = new StackQueueManager();
        HashTableManager htm = new HashTableManager();

        int choice;

        do {

            System.out.println("\n--- Academic Planner ---");
            System.out.println("1. Add Assignment");
            System.out.println("2. Display Assignments");
            System.out.println("3. Search Assignment");
            System.out.println("4. Sort by Due Date");
            System.out.println("5. Undo Last");
            System.out.println("6. Show Reminders");
            System.out.println("7. Search using HashTable");
            System.out.println("0. Exit");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Title: ");
                    String title = sc.nextLine();

                    System.out.print("Course: ");
                    String course = sc.nextLine();

                    System.out.print("Due Date (DD/MM/YYYY): ");
                    String date = sc.nextLine();

                    System.out.print("Progress: ");
                    int progress = sc.nextInt();

                    Assignment a = new Assignment(title, course, date, progress);

                    list.insert(a);
                    sqm.pushUndo(a);
                    sqm.addReminder(a);
                    htm.insert(a);

                    break;

                case 2:
                    list.display();
                    break;

                case 3:
                    System.out.print("Enter title: ");
                    String searchTitle = sc.nextLine();
                    SearchSort.search(list.head, searchTitle);
                    break;

                case 4:
                    SearchSort.sort(list.head);
                    System.out.println("Assignments sorted by due date.");
                    break;

                case 5:
                    sqm.undoLast();
                    break;

                case 6:
                    sqm.processReminders();
                    break;

                case 7:
                    System.out.print("Enter title: ");
                    String hashTitle = sc.nextLine();
                    htm.search(hashTitle);
                    break;

            }

        } while (choice != 0);

        sc.close();
    }
}