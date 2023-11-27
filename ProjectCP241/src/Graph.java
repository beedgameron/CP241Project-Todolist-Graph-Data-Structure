import java.util.*;
class Graph {

    ArrayList<LinkedList<Node>> alist;

    Graph() {
        alist = new ArrayList<>();
    }

    public void removeNode(String data) {
        Iterator<LinkedList<Node>> iterator = alist.iterator();
        while (iterator.hasNext()) {
            LinkedList<Node> currentList = iterator.next();
            if (currentList.getFirst().data.equals(data)) {
                iterator.remove();
                break;
            }
        }     // ลบ Node โดยทำการ loop LinkedList ที่เก็บข้อมูล Node โดยกำหนด iterator เป็นตัวเข้าถึง alist
                //แล้วทำการ loop โดยใช้คำสั่ง hasNext เพื่อเลื่อนไปข้อมูลถัดไปจนโดยกำหนด currentList ให้เป็นตัวที่อยู่ในปัจจุบัน
                //สร้างเงื่อนไขเมื่อ data(ตัวที่ต้องการลบ) เท่ากับ curremtList ปัจจุบันให้ iterator ลบออก

        // ลบ vertex(node) ที่ต้องการออกจาก adjacency list เห้ย!!   หรือก็คืออกจาก node ที่ชี้ไปหาตัวที่ลบ
        for (LinkedList<Node> currentList : alist) {
            Iterator<Node> nodeIterator = currentList.iterator();
            while (nodeIterator.hasNext()) {
                Node node = nodeIterator.next();
                if (node.data.equals(data)) {
                    nodeIterator.remove();
                    break;
                }
            }
        }
    }

    // เพิ่มเมทอด removeEdge ใน Graph class
    public void removeEdge(String src, String dst) {
        LinkedList<Node> srcList = getNodeList(src);

        if (srcList != null) {
            Iterator<Node> iterator = srcList.iterator();
            while (iterator.hasNext()) {
                Node node = iterator.next();
                if (node.data.equals(dst)) {
                    iterator.remove();
                    return; // Break out of the method after removing the edge
                }
            }

            System.out.println("Error: not found");
        } else {
            System.out.println("Error: Source node not found.");
        }
    }

    public void addNode(Node node) {
        LinkedList<Node> currentList = new LinkedList<>();
        currentList.add(node);
        alist.add(currentList);
    }

    public void addEdge(String src, String dst) {
        LinkedList<Node> srcList = getNodeList(src);
        LinkedList<Node> dstList = getNodeList(dst);

        if (srcList != null && dstList != null) {
            Node dstNode = getNode(dst);
            srcList.add(dstNode);
        } else {
            System.out.println("Error: Source or destination node not found");
        }
    }

    public boolean checkEdge(String src, String dst) {
        LinkedList<Node> currentList = getNodeList(src);
        Node dstNode = getNode(dst);

        for (Node node : currentList) {
            if (node.equals(dstNode)) {
                return true;
            }
        }
        return false;
    }

    private LinkedList<Node> getNodeList(String data) {
        for (LinkedList<Node> currentList : alist) {
            if (currentList.getFirst().data.equals(data)) {
                return currentList;
            }
        }
        return null; // จัดการกรณีที่ไม่พบโหนด
    }

    private Node getNode(String data) {
        for (LinkedList<Node> currentList : alist) {
            if (currentList.getFirst().data.equals(data)) {
                return currentList.getFirst();
            }
        }
        return null; // จัดการกรณีที่ไม่พบโหนด
    }

    public void print() {
        for (LinkedList<Node> currentList : alist) {
            for (Node node : currentList) {
                System.out.print(node.data + " -> ");
            }
            System.out.println();
        }
    }
}