package application;

import javafx.scene.Node;
import javafx.scene.control.ListCell;

public class RenderComboLigas<Liga> extends ListCell<Liga> {
    @Override public void updateItem(Liga item, boolean empty) {
        super.updateItem(item, empty);
 
        if (empty) {
            setText(null);
            setGraphic(null);
        } else if (item instanceof Node) {
            Node currentNode = getGraphic();
            Node newNode = (Node) item;
            if (currentNode == null || ! currentNode.equals(newNode)) {
            	setGraphic(newNode);
            }
        } else {
            setText(item == null ? "null" : item.toString());
            setGraphic(null);
        }
    }
}