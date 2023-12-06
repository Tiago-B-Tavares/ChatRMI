package ChatRmi;

import java.rmi.Naming;
import javax.swing.*;
import java.rmi.RemoteException;

public class ChatClient {

    public static void main(String args[]) {
        try {
            // Obtém a referência remota para a interface de chat
            final ChatInterface chat = (ChatInterface) Naming.lookup("rmi://localhost:1098/ChatInterface");

            // Solicita que o usuário entre com um nome
            String nomeUsuario = obterNomeUsuario();

            // Cria e configura a interface gráfica do chat
            FrmChat telaChat = configurarInterfaceGrafica();

            // Exibe uma mensagem indicando que o usuário entrou no grupo
            exibirMensagemEntrada(telaChat, nomeUsuario);

            // Cria uma thread para receber mensagens do servidor de forma assíncrona
            iniciarThreadRecebimento(chat, telaChat);

            // Aguarda a interação do usuário no loop principal
            aguardarInteracaoUsuario(chat, telaChat, nomeUsuario);

        } catch (Exception e) {
            // Em caso de exceção, exibe uma mensagem de erro
            System.out.println("Exceção: " + e.getMessage());
        }
    }

    // Método para solicitar o nome do usuário
    private static String obterNomeUsuario() {
        String nomeUsuario = "";
        while (nomeUsuario == null || nomeUsuario.equals("")) {
            nomeUsuario = JOptionPane.showInputDialog("Nome");
        }
        return nomeUsuario;
    }

    // Método para criar e configurar a interface gráfica do chat
    private static FrmChat configurarInterfaceGrafica() {
        FrmChat telaChat = new FrmChat();
        telaChat.setVisible(true);
        return telaChat;
    }

    // Método para exibir mensagem de entrada no grupo
    private static void exibirMensagemEntrada(FrmChat telaChat, String nomeUsuario) {
        telaChat.setTextArea(nomeUsuario + " entrou no grupo de conversa \n");
    }

    // Método para iniciar a thread de recebimento de mensagens assíncronas
    private static void iniciarThreadRecebimento(ChatInterface chat, FrmChat telaChat) {
        Thread thread = new Thread(() -> {
            try {
                int tamanhoArray = chat.lerMensagem().size();
                while (true) {
                    if (chat.lerMensagem().size() > tamanhoArray) {
                        telaChat.setTextArea(chat.lerMensagem().get(chat.lerMensagem().size() - 1));
                        tamanhoArray++;
                    }
                }
            } catch (RemoteException e) {
                System.out.printf("Ocorreu um erro, reinicie o programa!\n");
                System.out.println("Exceção: " + e.getMessage());
                System.exit(0);
            }
        });
        thread.start();
    }

    // Método para aguardar a interação do usuário no loop principal
    private static void aguardarInteracaoUsuario(ChatInterface chat, FrmChat telaChat, String nomeUsuario) throws RemoteException {
        boolean sair = false;
        while (!sair) {
            System.out.println(telaChat.isClicou());

            if (telaChat.isClicou()) {
                String mensagemChat = nomeUsuario + ": " + telaChat.getTextField() + "\n";
                if (!telaChat.getTextField().equals("")) {
                    chat.enviarMensagem(mensagemChat);
                    telaChat.setTextField("");
                }
            }
            telaChat.setClicou(false);
        }
    }
}
