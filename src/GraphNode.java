import java.util.*;

public class GraphNode {
    public int id;
    public List<Integer> next = new ArrayList<>();//下一个节点的编号
    public HashSet<Integer> isReachedStatesSet = new HashSet<>();



    public Map<String, Boolean> properties = new HashMap<>();//原子命题的真假

    public HashSet<Integer> getIsReachedStatesSet() {
        return isReachedStatesSet;
    }

    public void setIsReachedStatesSet(HashSet<Integer> isReachedStatesSet) {
        this.isReachedStatesSet = isReachedStatesSet;
    }

    public Map<String, Boolean> nodes = new HashMap<>();//

    public GraphNode() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getNext() {
        return next;
    }

    public void setNext(List<Integer> next) {
        this.next = next;
    }

    public Map<String, Boolean> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Boolean> properties) {
        this.properties = properties;
    }

    public Map<String, Boolean> getNodes() {
        return nodes;
    }

    public void setNodes(Map<String, Boolean> nodes) {
        this.nodes = nodes;
    }

    public GraphNode(int id, List<Integer> next, Map<String, Boolean> properties, Map<String, Boolean> nodes) {
        this.id = id;
        this.next = next;
        this.properties = properties;
        this.nodes = nodes;
    }
}
