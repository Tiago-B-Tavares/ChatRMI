package ChatRmi;

import java.rmi.Naming;
import javax.swing.*;
import java.rmi.RemoteException;


public class ChatClient {

    public static void main(String args[]) {

        try {//bloco protegido
            final ChatInterface chat = (ChatInterface) Naming.lookup("rmi://localhost:1098/ChatInterface");// procura um objeto no stub local
            final int controle = 2;
            String nomeUsuario = "";
            String msgEnviada;

            System.out.println(chat.lerMensagem());

            FrmChat meuchat = new FrmChat();


            while (nomeUsuario == null || nomeUsuario.equals("")) {
                nomeUsuario = (JOptionPane.showInputDialog("Nome"));//janela para o nome.
                chat.enviarMensagem("\t\t\t" + nomeUsuario + " Entrou em um grupo de conversa\n");// enviando mensagem para o servidor
            }
            meuchat.setVisible(true);
            meuchat.setTextArea("\nVocê entrou em um grupo de conversa " + nomeUsuario + "\n");

            Thread thread = new Thread(new Runnable() {
                int cont = chat.lerMensagem().size();// tamanho do array

                @Override
                public void run() {
                    try {

                        while (controle == 2) {//processo independente THREAD para receber as mensagens enviadas para o servidor

                            if (chat.lerMensagem().size() > cont) {//se chegar uma nova mensagem

                                meuchat.setTextArea(chat.lerMensagem().get(chat.lerMensagem().size() - 1));//imprime sempre as ultimas mensagens enviadas ao servidor
                                cont++;//contador
                            }
                        }
                    } catch (RemoteException e) {
                        System.out.printf("Ocorreu um erro, reinicie o programa!\n");
                        System.out.println("Exeção: " + e.getMessage());
                        System.exit(0);
                    }
                }
            });
            thread.start();
            int sair = 0;//variavel sair recebe incialização 0
            while (sair == 0) {//laço de repetição

                Thread.currentThread().sleep(2000);
                System.out.println(meuchat.isClicou());
                if (meuchat.isClicou()) {
                    msgEnviada = nomeUsuario + ": " + meuchat.getTextField() + "\n";
                    // Caso o usuario tenha digitado algo.
                    if (!meuchat.getTextField().equals("")) {
                        // Envia a mensagem para a interface para retornar ao usuário novamente.
                        chat.enviarMensagem(msgEnviada);
                        meuchat.setTextField("");
                    }
                }
                meuchat.setClicou(false);
            }
        } catch (Exception e) {
            System.out.println("Exeção:2 " + e.getMessage() + "Exeção 2");
        }
    }
}
