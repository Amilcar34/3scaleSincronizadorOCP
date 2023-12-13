package app;

import java.io.*;
public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        String interpreter = "git clone git@ssh.dev.azure.com:v3/ASEConecta/Auditoria%20de%20Terreno/3scale";
        Process process = Runtime.getRuntime().exec(interpreter);
        print(process.getInputStream());
        print(process.getErrorStream());
        process.waitFor();
        int exitStatus = process.exitValue();
        System.out.println("exit status: " + exitStatus);
        File[] files = new File("").listFiles();
        System.out.println("number of files in the directory: " + files.length);
    }

    public static void print(InputStream input) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader bf = new BufferedReader(new InputStreamReader(input));
                String line = null;
                try {
                    while ((line = bf.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    System.out.println("IOException");
                }
            }
        }).start();
    }
}
