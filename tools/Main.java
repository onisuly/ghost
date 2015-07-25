import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by onisuly on 7/18/15.
 */
public class Main {

    public static void main(String[] args) {
        String filePath = System.getProperty("user.dir");
        filePath = filePath.substring(0, filePath.lastIndexOf('/'));
        String installFile = filePath + "/ghost/content/install.rdf";
        String projectFile = filePath + "/build/ghost.rdf";
        String contentDir = filePath + "/ghost/content/";

        if ( args[0].equals("local") ) {
            raiseBuildVersion(installFile);
            raiseBuildVersion(projectFile);
            buildFile(contentDir, "ghost.xpi");
        }
        else if ( args[0].equals("online") ) {
            installFile = filePath + "/ghost - online/content/install.rdf";
            projectFile = filePath + "/build/ghost-online.rdf";
            contentDir = filePath + "/ghost - online/content/";
            raiseBuildVersion(installFile);
            raiseBuildVersion(projectFile);
            buildFile(contentDir, "ghost-online.xpi");
        }
    }

    public static void raiseBuildVersion(String fileName) {
        try {
            BufferedReader reader = new BufferedReader( new FileReader(fileName) );
            StringBuilder stringBuilder = new StringBuilder();
            String strTemp;
            while ( ( strTemp = reader.readLine() ) != null ) {
                stringBuilder.append(strTemp);
                stringBuilder.append("\n");
            }
            reader.close();
            Pattern pattern = Pattern.compile("<em:version>([0-9]+)\\.([0-9]+)\\.([0-9]+)</em:version>");
            Matcher matcher = pattern.matcher(stringBuilder);
            StringBuffer stringBuffer = new StringBuffer();
            if ( matcher.find() ) {
                matcher.appendReplacement(stringBuffer, "<em:version>$1.$2." +
                        (1 + Integer.parseInt(matcher.group(3))) + "</em:version>");
            }
            matcher.appendTail(stringBuffer);
            BufferedWriter writer = new BufferedWriter( new FileWriter(fileName) );
            writer.write(stringBuffer.toString());
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void buildFile(String contentDir, String fileName) {
        try {
            File workingDir = new File(contentDir);
            String[] cmd = {"zip", "-FSr", "../../build/" + fileName, "."};
            Process pos = Runtime.getRuntime().exec(cmd, null, workingDir);
            pos.waitFor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
