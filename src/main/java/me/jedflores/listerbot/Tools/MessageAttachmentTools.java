package me.jedflores.listerbot.Tools;

import net.dv8tion.jda.api.entities.Message;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class MessageAttachmentTools {

    private static String readFileAttachment(Message.Attachment attachment){
        AtomicReference<String> output = new AtomicReference<>("");

        try {
            attachment.getProxy().download().thenAccept(fileContent ->{
                System.out.println(attachment.getFileName()+" downloaded successfully!");
                Scanner scanner = new Scanner(fileContent).useDelimiter("\\A");
                String result = scanner.hasNext() ? scanner.next() : "";
                System.out.println(result);
                output.set("**"+attachment.getFileName()+"**"+"\n"+result);
                scanner.close();
            }).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return output.toString();
    }
    public static List<String> readFileAttachements(List<Message.Attachment> attachments){
        List<String> outputs = new ArrayList<>();
        AtomicReference<String> output = new AtomicReference<>("");
        if (attachments.isEmpty()){
            output.set("No attachments found!");
        }
        else{
            for (Message.Attachment file:attachments) {
//                            System.out.println(" filename: "+file.getFileName());
//                            System.out.println(" file extension: "+file.getFileExtension());
//                            System.out.println(" file content type: "+file.getContentType());
//                            System.out.println(" file size: "+file.getSize());
                outputs.add(readFileAttachment(file)+"\n\n");

            }
        }
        System.out.println("outputs size: "+outputs.size());
        return outputs;
    }
}
