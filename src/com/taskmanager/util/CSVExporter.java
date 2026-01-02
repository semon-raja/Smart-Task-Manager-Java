package com.taskmanager.util;

import com.taskmanager.model.Task;
import java.io.FileWriter;
import java.util.List;

public class CSVExporter {

    public static void exportTasks(List<Task> tasks, String filePath) throws Exception {
        FileWriter fw = new FileWriter(filePath);
        fw.write("Title,Category,Deadline,Priority\n");

        for (Task t : tasks) {
            fw.write(
                t.getTitle() + "," +
                t.getCategory() + "," +
                t.getDeadline() + "," +
                t.getPriority() + "\n"
            );
        }

        fw.close();
    }
}
