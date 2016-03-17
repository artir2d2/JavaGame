package GuiUtil;

import TCPUtil.Client;
import TCPUtil.Server;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import javax.swing.JTextField;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JProgressBar;

public class GameGui extends JFrame{
	private JPanel mainMenu;
	private JPanel contentPane;
	private JPanel joinGameMenu;
	private JTextField serverAddresTxt;
	private CardLayout cl = new CardLayout();
	private JTextField nickname;
	private JTextField nicknameTxt1;
	private JSpinner portValue;
	private JSpinner portJoin;
	private JSpinner mapSize;
	private JProgressBar progressBar;
	public static String messageToPop;
	private JLabel serverMsg;
	private final Action BTMM = new BackToMainMenu();
	private final Action joinGameMenuPop = new JoinGameMenuPop();
	private final Action createGameMenuPop = new CreateGameMenuPop();
	private final Action joinGame1 = new JoinGame();
	private final Action createGame1 = new CreateGame();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGui frame = new GameGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GameGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(cl);
		/*
		 * Main menu
		 */
		String BGImage = new String("BGImage.png");
		mainMenu = new ImagePanel(BGImage);
		contentPane.add(mainMenu, "main_menu");
		GridBagLayout gbl_mainMenu = new GridBagLayout();

		mainMenu.setLayout(gbl_mainMenu);
		JButton joinGameBtn = new JButton("Join Game");
		joinGameBtn.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		joinGameBtn.setAction(joinGameMenuPop);
		joinGameBtn.setPreferredSize(new Dimension(200, 50));
		GridBagConstraints gbc_joinGameBtn = new GridBagConstraints();
		gbc_joinGameBtn.insets = new Insets(0, 0, 5, 0);
		gbc_joinGameBtn.gridx = 0;
		gbc_joinGameBtn.gridy = 0;
		mainMenu.add(joinGameBtn, gbc_joinGameBtn);

		JButton createGameBtn = new JButton("Create Game");
		createGameBtn.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		createGameBtn.setAction(createGameMenuPop);
		createGameBtn.setPreferredSize(new Dimension(200, 50));
		GridBagConstraints gbc_createGameBtn = new GridBagConstraints();
		gbc_createGameBtn.gridx = 0;
		gbc_createGameBtn.gridy = 1;
		mainMenu.add(createGameBtn, gbc_createGameBtn);
		/*
		 * Join Game menu
		 */
		joinGameMenu = new ImagePanel(BGImage);
		contentPane.add(joinGameMenu, "join");
		GridBagLayout gbl_joinGameMenu = new GridBagLayout();
		joinGameMenu.setLayout(gbl_joinGameMenu);

		nickname = new JTextField("Nickname");
		nickname.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		nickname.setPreferredSize(new Dimension(400, 30));
		GridBagConstraints gbc_nickname = new GridBagConstraints();
		gbc_nickname.insets = new Insets(0, 0, 5, 0);
		gbc_nickname.fill = GridBagConstraints.HORIZONTAL;
		gbc_nickname.gridx = 1;
		gbc_nickname.gridy = 0;
		joinGameMenu.add(nickname, gbc_nickname);
		nickname.setColumns(10);

		serverAddresTxt = new JTextField("localhost");
		serverAddresTxt.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		serverAddresTxt.setPreferredSize(new Dimension(400, 30));
		GridBagConstraints gbc_serverAddresTxt = new GridBagConstraints();
		gbc_serverAddresTxt.insets = new Insets(0, 0, 5, 0);
		gbc_serverAddresTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_serverAddresTxt.gridx = 1;
		gbc_serverAddresTxt.gridy = 1;
		joinGameMenu.add(serverAddresTxt, gbc_serverAddresTxt);
		serverAddresTxt.setColumns(10);

		JLabel portLabel = new JLabel("Port");
		portLabel.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		GridBagConstraints gbc_portLabel = new GridBagConstraints();
		gbc_portLabel.insets = new Insets(0, 0, 5, 5);
		gbc_portLabel.gridx = 0;
		gbc_portLabel.gridy = 2;
		joinGameMenu.add(portLabel, gbc_portLabel);

		serverMsg = new JLabel("");
		serverMsg.setFont(new Font("Viner Hand ITC", Font.BOLD, 18));
		Font font = new Font("Viner Hand ITC", Font.BOLD, 18);
		GridBagConstraints gbc_ServerMsg = new GridBagConstraints();
		//gbc_ServerMsg.insets = new Insets(0, 0, 5, 5);
		gbc_ServerMsg.gridx = 1;
		gbc_ServerMsg.gridy = 5;
		joinGameMenu.add(serverMsg, gbc_ServerMsg);

		portJoin = new JSpinner();
		GridBagConstraints gbc_portJoin = new GridBagConstraints();
		portJoin.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		portJoin.setValue(80);
		gbc_portJoin.fill = GridBagConstraints.HORIZONTAL;
		gbc_portJoin.insets = new Insets(0, 0, 5, 0);
		gbc_portJoin.gridx = 1;
		gbc_portJoin.gridy = 2;
		joinGameMenu.add(portJoin, gbc_portJoin);

		JButton joinGameBtn1 = new JButton("Join Game");
		joinGameBtn1.setAction(joinGame1);
		joinGameBtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		joinGameBtn1.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		joinGameBtn1.setPreferredSize(new Dimension(200, 50));
		GridBagConstraints gbc_joinGameBtn1 = new GridBagConstraints();
		gbc_joinGameBtn1.insets = new Insets(0, 0, 5, 0);
		gbc_joinGameBtn1.gridx = 1;
		gbc_joinGameBtn1.gridy = 3;
		joinGameMenu.add(joinGameBtn1, gbc_joinGameBtn1);

		JButton backToMainMenuBtn = new JButton("Main menu");
		backToMainMenuBtn.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		backToMainMenuBtn.setAction(BTMM);
		GridBagConstraints gbc_backToMainMenuBtn = new GridBagConstraints();
		gbc_backToMainMenuBtn.gridx = 1;
		gbc_backToMainMenuBtn.gridy = 4;
		joinGameMenu.add(backToMainMenuBtn, gbc_backToMainMenuBtn);
		/*
		 * Create Game menu
		 */
		JPanel createGameMenu = new ImagePanel(BGImage);
		contentPane.add(createGameMenu, "create");
		GridBagLayout gridBagLayout = new GridBagLayout();
		createGameMenu.setLayout(gridBagLayout);

		nicknameTxt1 = new JTextField("Nickname");
		nicknameTxt1.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		nicknameTxt1.setPreferredSize(new Dimension(400, 30));
		GridBagConstraints gbc_nicknameTxt1 = new GridBagConstraints();
		gbc_nicknameTxt1.insets = new Insets(0, 0, 5, 0);
		gbc_nicknameTxt1.fill = GridBagConstraints.HORIZONTAL;
		gbc_nicknameTxt1.gridx = 1;
		gbc_nicknameTxt1.gridy = 0;
		createGameMenu.add(nicknameTxt1, gbc_nicknameTxt1);
		nicknameTxt1.setColumns(10);

		JLabel portValueLabel = new JLabel("Port number");
		portValueLabel.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		GridBagConstraints gbc_portValueLabel = new GridBagConstraints();
		gbc_portValueLabel.insets = new Insets(0, 0, 5, 5);
		gbc_portValueLabel.gridx = 0;
		gbc_portValueLabel.gridy = 1;
		createGameMenu.add(portValueLabel, gbc_portValueLabel);

		portValue = new JSpinner();
		portValue.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		portValue.setValue(80);
		GridBagConstraints gbc_portValue = new GridBagConstraints();
		gbc_portValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_portValue.insets = new Insets(0, 0, 5, 0);
		gbc_portValue.gridx = 1;
		gbc_portValue.gridy = 1;
		createGameMenu.add(portValue, gbc_portValue);

		JLabel mapSizeLabel = new JLabel("Map size");
		mapSizeLabel.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		GridBagConstraints gbc_mapSizeLabel = new GridBagConstraints();
		gbc_mapSizeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_mapSizeLabel.gridx = 0;
		gbc_mapSizeLabel.gridy = 2;
		createGameMenu.add(mapSizeLabel, gbc_mapSizeLabel);

		mapSize = new JSpinner();
		mapSize.setValue(150);
		mapSize.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		GridBagConstraints gbc_mapSize = new GridBagConstraints();
		gbc_mapSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_mapSize.insets = new Insets(0, 0, 5, 0);
		gbc_mapSize.gridx = 1;
		gbc_mapSize.gridy = 2;
		createGameMenu.add(mapSize, gbc_mapSize);

		JButton createGameBtn1 = new JButton("Create game");
		createGameBtn1.setAction(createGame1);
		createGameBtn1.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		createGameBtn1.setPreferredSize(new Dimension(200, 50));
		GridBagConstraints gbc_createGameBtn1 = new GridBagConstraints();
		gbc_createGameBtn1.insets = new Insets(0, 0, 5, 0);
		gbc_createGameBtn1.gridx = 1;
		gbc_createGameBtn1.gridy = 3;
		createGameMenu.add(createGameBtn1, gbc_createGameBtn1);

		JButton backToMainMenuBtn1 = new JButton("Main menu");
		backToMainMenuBtn1.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));
		backToMainMenuBtn1.setAction(BTMM);
		GridBagConstraints gbc_backToMainMenuBtn1 = new GridBagConstraints();
		gbc_backToMainMenuBtn1.insets = new Insets(0, 0, 5, 0);
		gbc_backToMainMenuBtn1.gridx = 1;
		gbc_backToMainMenuBtn1.gridy = 4;
		createGameMenu.add(backToMainMenuBtn1, gbc_backToMainMenuBtn1);

		progressBar = new JProgressBar(0,94);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.gridx = 1;
		gbc_progressBar.gridy = 5;
		createGameMenu.add(progressBar, gbc_progressBar);
	}

	private class JoinGameMenuPop extends AbstractAction {
		public JoinGameMenuPop() {
			putValue(NAME, "Join Game");
			putValue(SHORT_DESCRIPTION, "Go to join game menu");
		}
		public void actionPerformed(ActionEvent e) {
			cl.show(contentPane, "join");
		}
	}
	private class CreateGameMenuPop extends AbstractAction {
		public CreateGameMenuPop() {
			putValue(NAME, "Create Game");
			putValue(SHORT_DESCRIPTION, "Go to create game menu");
		}
		public void actionPerformed(ActionEvent e) {
			cl.show(contentPane, "create");
		}
	}
	private class BackToMainMenu extends AbstractAction {
		public BackToMainMenu() {
			putValue(NAME, "Back to Main Menu");
			putValue(SHORT_DESCRIPTION, "Leave to menu");
		}
		public void actionPerformed(ActionEvent e) {
			cl.show(contentPane, "main_menu");
		}
	}
	private class JoinGame extends AbstractAction implements Runnable{
		public JoinGame() {
			putValue(NAME, "Join Game");
			putValue(SHORT_DESCRIPTION, "Have fun!");
		}
		public void actionPerformed(ActionEvent e) {
			Thread cgt = new Thread(this);
			cgt.start();
			joinGame1.setEnabled(false);

		}
		@Override
		public void run() {
			//Player player = new Player(nickname.getText(),0,0,"grafiki//hero1.png");
			int port = (int)portJoin.getValue();
			String serverAddress = serverAddresTxt.getText();
			Client client;
			try {
				client = new TCPUtil.Client(serverAddress,port,nickname.getText());
				if(messageToPop!=null){
					serverMsg.setText(messageToPop);
					serverMsg.updateUI();
				}
				Thread clientThread = new Thread(client);
				clientThread.start();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class CreateGame extends AbstractAction implements Runnable{
		ExecutorService exec= Executors.newCachedThreadPool();
		Server server = null; //create host server
		public CreateGame() {
			putValue(NAME, "Create Game");
			putValue(SHORT_DESCRIPTION, "Creates a new game session and connects a host player to the freshly created server!");
		}
		public void actionPerformed(ActionEvent e) {
			Thread cgt = new Thread(this);
			cgt.start();
			createGame1.setEnabled(false);
		}

		@Override
		public void run() {
			String nickName = nicknameTxt1.getText();
			int port = (int)portValue.getValue();
			int mapSizeInt = (int)mapSize.getValue();
			try {
				server = new Server(port,mapSizeInt);
				/**
				 * Progress bar actualization thread
				 */
				Thread progresUpdate = new Thread(new Runnable() {
					@Override
					public void run() {
						while(server.returnWorldMapProgress()<1.){
							progressBar.setValue((int)(100*server.returnWorldMapProgress()));
							try {
								Thread.currentThread();
								Thread.sleep(1);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					}
				});
				Thread serverThread = new Thread(server);
				this.exec.execute(progresUpdate);
				this.exec.execute(serverThread);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Client client; //creating host client
			try {
				client = new TCPUtil.Client("localhost",port,nickName);
				if(messageToPop!=null){
					serverMsg.setText(messageToPop);
					serverMsg.updateUI();
				}
				Thread clientThread = new Thread(client);
				this.exec.execute(clientThread);//.start();
			} catch (IOException e1) {
				e1.printStackTrace();

			}

		}
	}


}

