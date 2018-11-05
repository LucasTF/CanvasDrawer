package app.xml;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public final class FileManager {
	
	private FileManager() {
		
	}
	
	public static File loadFile(String extension) {
		FileChooser ch = new FileChooser();
		ch.setTitle("Carregar Desenho");
		ch.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivo " + extension.toUpperCase(), "*." + extension.toLowerCase()));
		File file = ch.showOpenDialog(new Stage());
		return file;
	}
	
	public static File saveFile(String extension) {
		FileChooser ch = new FileChooser();
		ch.setTitle("Salvar Desenho");
		ch.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivo " + extension.toUpperCase(), "*." + extension.toLowerCase()));
		File file = ch.showSaveDialog(new Stage());
		return file;
	}
	
}
