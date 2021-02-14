/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GaussianEditor;

import java.io.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import javax.swing.*;

/**
 *
 * @author Hoimin
 */
public class DnDText extends JFrame implements DropTargetListener{

    JTextArea editor;
    DropTarget target;
   
    public DnDText(){
        super("Drag & Drop");
        editor = new JTextArea();
        target = new DropTarget(editor,DnDConstants.ACTION_COPY_OR_MOVE,
                (DropTargetListener) this,true,null);
        getContentPane().add("Center",new JScrollPane(editor));
       
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450,500);
        setVisible(true);
    }
    /* DropTargetListener 인터페이스 구현을 위한 부분 */
    public void dragEnter(DropTargetDragEvent dtde){
        System.out.println("dragEnter");
    }
    public void dragExit(DropTargetEvent dtde){
        System.out.println("dragExit");
    }
    public void dragOver(DropTargetDragEvent dtde){
        System.out.println("dragOver");
    }
    public void drop(DropTargetDropEvent dtde){
        System.out.println("drop");
        //액션이 copy or move인 경우에 읽어들인다.
        if((dtde.getDropAction() &
                DnDConstants.ACTION_COPY_OR_MOVE)!=0){
            dtde.acceptDrop(dtde.getDropAction());
            Transferable tr = dtde.getTransferable();
            try{
                //전달되는 파일을 리스트형태로 변환
                //파일리스트의 DataFlavor를 이용하여 tr에 저장
                java.util.List list = (java.util.List)
                tr.getTransferData(DataFlavor.javaFileListFlavor);
                //리스트의 첫번째 원소를 파일로 읽어들인다.
                File file = (File)list.get(1);
                char buf[] = new char[1024];
                BufferedReader in = new BufferedReader(new  FileReader(file));
                int n = -1;
                editor.setText("");
                while((n=in.read(buf,0,1024))!=-1){
                    editor.append(new String(buf,0,n));
                }
                in.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public void dropActionChanged(DropTargetDragEvent dtde){
        System.out.println("dropActionChanged");
    }
    /*
        public static void main(String[] args) {
        new DnDText();
    }
    */     
}
