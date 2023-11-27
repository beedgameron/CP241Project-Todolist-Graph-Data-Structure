import java.util.Objects;

public class Node {

    String data;

    Node(String data) {
        this.data = data;
    }

    // โอเวอร์ไรด์เมทอด equals เพื่อเปรียบเทียบโหนดตามข้อมูล
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return Objects.equals(data, node.data);
    }
}