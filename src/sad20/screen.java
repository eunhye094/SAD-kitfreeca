package sad20;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.*;
import java.io.ObjectInputFilter.Status;
import java.util.*;
import java.text.SimpleDateFormat;

class UserInfo{
	private String name;
	private String id;
	private String pw;
	private String position;

	public UserInfo(String a, String b, String c, String d) {
		name = a;
		id = b;
		pw = c;
		position = d;
	}
	public String getUserName() {
		return name;
	}
	public String getUserID() {
		return id;
	}
	public String getUserPW() {
		return pw;
	}
	public String getUserPosition() {
		return position;
	}
}

abstract class MediaInfo{
	protected String id;
	protected String title;
	protected String type;
	protected String category;
	protected String donation;
	protected String thumbnail;
	protected int donation_num; //��α�
	protected int good_num; //��õ��
	
	abstract public String [] getMediaAllInfo();
	abstract public int [] getMediaNumInfo();
	
	public void addDonation() {
		donation_num+=100;
	}
	public void addGood() {
		good_num+=1;
	}
}

class Video_Music extends MediaInfo{
	private String time;
	private int play_num; //������ ����� ��
	
	public Video_Music(String a, String b, String c, String d, String e, String f, String g, int h, int i, int j) {
		id = a;
		title = b;
		thumbnail = c;
		type = d;
		category = e;
		donation = f;
		time = g;
		donation_num = h;
		good_num = i;
		play_num = j;
	}
	public String [] getMediaAllInfo() {
		String all[] = {id, title, thumbnail, type, category, donation, time};
		return all;
	}
	public int [] getMediaNumInfo() {
		int all[] = {donation_num, good_num, play_num};
		return all;
	}
	public void addPlay() {
		play_num+=1;
	}
}

class Novel_Comic extends MediaInfo{
	private int bookmark_num; //�ʿ���� ����
	
	public Novel_Comic(String a, String b, String c, String d, String e, String f, int h, int i, int j) {
		id = a;
		title = b;
		thumbnail = c;
		type = d;
		category = e;
		donation = f;
		donation_num = h;
		good_num = i;
		bookmark_num = j;
	}
	public String [] getMediaAllInfo() {
		String all[] = {id, title, thumbnail, type, category, donation};
		return all;
	}
	public int [] getMediaNumInfo() {
		int all[] = {donation_num, good_num, bookmark_num};
		return all;
	}
}

class Bookmark {
	private String media;
	private Vector<String> user = new Vector<String>();
	
	public Bookmark(String a, Vector<String> b) {
		media = a;
		user = b;
	}
	
	public String getBook() {
		return media;
	}
	public Vector<String> getBookUser(){
		return user;
	}
}

public class screen extends JFrame {

	//���� ����
	private JPanel contentPane; //main_screen
	private JTextField �˻�â;
	private JButton �α���;
	private JButton �α׾ƿ�;
	private JButton ȸ������;
	private JButton ȸ��Ż��;
	private JButton ȸ������;
	private JLabel ȯ��;
	
	private JPanel panel_03 = new JPanel();
	private JPanel [] panel_�̵�� = new JPanel[60]; //�̵�� Panel
	
	private JPanel contentPane2; //sign_up_screen
	private JTextField name_input;
	private JTextField id_input;
	private JTextField pw_input;
	
	private JPanel contentPane3; //login_screen
	private JTextField ID_input;
	private JTextField PW_input;
	
	private JPanel contentPane4; //upload_screen
	private JTextField title_input;
	private JTextField thumbnail_input;
	
	private JPanel contentPane5; //manager_screen
	private JTextField manager_input;
	private FileDialog Image_fopen;
	private String Image_fopen_name;
	
	private JPanel contentPane6;
	
	private JPanel I_panel_02 = new JPanel();
	private JPanel I_panel_03 = new JPanel();
	private JButton [] func_B = new JButton[2];
	private JLabel ���;
	private JLabel ��õ;
	private JLabel ���;
	private int donation_num;
	private int good_num;
	private int etc_num;
	
	private static Vector<UserInfo> UserList = new Vector<UserInfo>();
	private static Vector<Video_Music> Video_Music_List = new Vector<Video_Music>();
	private static Vector<Novel_Comic> Novel_Comic_List = new Vector<Novel_Comic>();
	private static Vector<Bookmark> Bookmark_List = new Vector<Bookmark>();
	
	private String loginID = null;
	private String loginPosition = null;
	private int mediaCount = 0;
	private int ImageCount = 0;
	
	private String manager_PW = "To infinity and beyond";
	private String Selected_Type = "��ü";
	private String Selected_Category = "��ü";
	
	private String ����_btn_name[] = {"��ü", "������", "����", "�Ҽ�", "��ȭ"};
	private String ī�װ�_btn_name[] = {"��ü", "����", "������", "�н�", "����"};
	private JButton [] ����_btn = new JButton[����_btn_name.length];
	private JButton [] ī�װ�_btn = new JButton[ī�װ�_btn_name.length];

	public static void main(String[] args) {
		
	}
	
	public boolean checkBookmark(String in_media, String in_id) {
		for (int i=0; i<Bookmark_List.size();i++) {
			if (Bookmark_List.get(i).getBook().equals(in_media)) {
				Vector<String> temp = new Vector<String>();
				temp = Bookmark_List.get(i).getBookUser();
				for (int j=0;j<temp.size();j++) {
					if (temp.get(j).equals(in_id))
						return true;
				}
			}
		}
		return false;
	}
	
	public void SetButtonColor() {
		for (int i=0;i<����_btn_name.length;i++) {
			����_btn[i].setForeground(new Color(0, 0, 0));
			����_btn[i].setBackground(Color.white);
		}
		for (int i=0;i<ī�װ�_btn_name.length;i++) {
			ī�װ�_btn[i].setForeground(new Color(0, 0, 0));
			ī�װ�_btn[i].setBackground(Color.white);	
		}
		����_btn[0].setForeground(new Color(255, 0, 0));
		����_btn[0].setBackground(SystemColor.controlHighlight);
		ī�װ�_btn[0].setForeground(new Color(255, 0, 0));
		ī�װ�_btn[0].setBackground(SystemColor.controlHighlight);
		Selected_Type = "��ü";
		Selected_Category = "��ü";
	}
	
	public String[] GetImageInfo(String t, String c, String d) {
		String tI=null, cI=null, dI=null;
		switch (t) { //����
		case "������": tI = "src\\images\\video.png";
			break;
		case "����": tI = "src\\images\\music.png";
			break;
		case "��ȭ": tI = "src\\images\\comic.png";
			break;
		case "�Ҽ�": tI = "src\\images\\novel.png";
			break;
		}
		switch (c) { //ī�װ�
		case "����": cI = "src\\images\\game.png";
			break;
		case "������": cI = "src\\images\\sport.png";
			break;
		case "�н�": cI = "src\\images\\study.png";
			break;
		case "����": cI = "src\\images\\food.png";
			break;
		}
		switch (d) { //���
		case "����": dI = "src\\images\\money.png";
			break;
		case "�Ұ���": dI = "src\\images\\ban.png";
			break;
		}
		String [] ImageInfo = {tI, cI, dI};
		return ImageInfo;
	}
	
	public String GetPosition(String loginID) {
		for (int i=0;i<UserList.size();i++) {
			if (UserList.get(i).getUserID().equals(loginID))
				return UserList.get(i).getUserPosition();
		}
		return null;
	}
	
	public int GetMediaIndex(String thumbnail, String type, String category, String donation, String time, String title, String id) {
		if (time.equals("Novel_Comic")) { //Novel_Comic
			String [] temp = {id, title, thumbnail, type, category, donation};
			for (int i=0;i<Novel_Comic_List.size();i++) {
				if (Arrays.equals(Novel_Comic_List.get(i).getMediaAllInfo(),temp))
					return i;
			}
		}
		else { //Video_Music
			String [] temp = {id, title, thumbnail, type, category, donation, time};
			for (int i=0;i<Video_Music_List.size();i++) {
				if (Arrays.equals(Video_Music_List.get(i).getMediaAllInfo(),temp))
					return i;
			}
		}
		return -1;
	}
	
	public boolean ExistMedia(String thumbnail, String type, String category, String donation, String time, String title, String id) {
		if (time.equals("Novel_Comic")) { //Novel_Comic
			String [] temp = {id, title, thumbnail, type, category, donation};
			for (int i=0;i<Novel_Comic_List.size();i++) {
				if (Arrays.equals(Novel_Comic_List.get(i).getMediaAllInfo(),temp))
					return true;
			}
		}
		else { //Video_Music
			String [] temp = {id, title, thumbnail, type, category, donation, time};
			for (int i=0;i<Video_Music_List.size();i++) {
				if (Arrays.equals(Video_Music_List.get(i).getMediaAllInfo(),temp))
					return true;
			}
		}
		return false;
	}
	
	public boolean NotExistTitle(String inputTitle) { //���� title�̸� true
		for (int i=0;i<Video_Music_List.size();i++) {
			if (Video_Music_List.get(i).getMediaAllInfo()[1].contains(inputTitle))
				return false;
		}
		for (int i=0;i<Novel_Comic_List.size();i++) {
			if (Novel_Comic_List.get(i).getMediaAllInfo()[1].contains(inputTitle))
				return false;
		}
		return true;
	}
	
	public boolean NotExistMyMedia(String loginID) { //myMedia ������ true
		for (int i=0;i<Video_Music_List.size();i++) {
			if (Video_Music_List.get(i).getMediaAllInfo()[0].equals(loginID))
				return false;
		}
		for (int i=0;i<Novel_Comic_List.size();i++) {
			if (Novel_Comic_List.get(i).getMediaAllInfo()[0].equals(loginID))
				return false;
		}
		return true;
	}
	
	public boolean NotExistUserID(String inputID) { //���� id�� true
		for (int i=0;i<UserList.size();i++) {
			if (UserList.get(i).getUserID().equals(inputID)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean CheckPosition(String DelID) { //�����ڸ� true
		for (int i=0;i<UserList.size();i++) {
			if (UserList.get(i).getUserID().equals(DelID)&&UserList.get(i).getUserPosition().equals("������"))
				return true;
		}
		return false;
	}
	
	public boolean CheckUserPW(String inputID,String inputPW) { //��� ��ġ�ϸ� true
		for (int i=0;i<UserList.size();i++) {
			if (UserList.get(i).getUserID().equals(inputID)&&UserList.get(i).getUserPW().equals(inputPW))
				return true;
		}
		return false;
	}
	
	public boolean CheckTumbnail(String Input_Thumbnail) { //��ġ�ϸ� true
		String [] Format_List = {".PNG", ".png", ".JPEG", ".jpeg", ".JPG", ".jpg"};
		for (int i=0;i<Format_List.length;i++) {
			if (Input_Thumbnail.contains(Format_List[i]))
				return true;
		}
		return false;
	}
	
	public String CheckTime(String time_01, String time_02) {
		String time;
		int num_01 = Integer.parseInt(time_01);
		int num_02 = Integer.parseInt(time_02);
		if (num_01<10)
			time_01="0".concat(time_01);
		if (num_02<10)
			time_02="0".concat(time_02);
		time = time_01.concat(":").concat(time_02);
		return time;
	}
	
	void ReadUserInfo() {
		File in = new File("src\\Text\\UserListText.txt");
		try 
		{
			Scanner s = new Scanner(in);
			while (s.hasNextLine())
			{
				String line = s.nextLine();

	            StringTokenizer st = new StringTokenizer(line, " ");
	            String [] Info = new String[4];
	            for (int i=0;i<4;i++)
	               Info[i]=st.nextToken();

	            UserList.add(new UserInfo(Info[0],Info[1],Info[2],Info[3]));
			}
			s.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("���� "+in.getName()+"�� ã�� �� �����ϴ�.");
			System.exit(0);
		}
	}
	
	void WriteUserInfo() {
		File out = new File("src\\Text\\UserListText.txt");
		try
		{
			FileWriter w = new FileWriter(out);
			for (int i = 0; i < UserList.size(); i++)
			{
				UserInfo ui = UserList.get(i);
				w.write(ui.getUserName()+" ");
				w.write(ui.getUserID()+" ");
				w.write(ui.getUserPW()+" ");
				w.write(ui.getUserPosition()+"\n");
			}
			w.close();
		}
		catch (IOException e)
		{
			System.out.println("IOException ������ �߻��߽��ϴ�.");
			System.exit(0);
		}
	}
	
	void DelUserInfo(String DelID) {
		for (int i=0;i<UserList.size();i++) {
			if (UserList.get(i).getUserID().equals(DelID))
				UserList.remove(i);
		}
		WriteUserInfo();

		int temp = 0;
		while(temp<Video_Music_List.size()) {
			if (Video_Music_List.get(temp).getMediaAllInfo()[0].equals(DelID))
				Video_Music_List.remove(temp);
			else
				temp++;
		}
		WriteVideo_MusicInfo();
		
		int temp2 = 0;
		while(temp2<Novel_Comic_List.size()) {
			if (Novel_Comic_List.get(temp2).getMediaAllInfo()[0].equals(DelID))
				Novel_Comic_List.remove(temp2);
			else
				temp2++;
		}
		WriteNovel_ComicInfo();
		
		mediaCount = Video_Music_List.size()+Novel_Comic_List.size();
	}
	
	void ReadVideo_MusicInfo() {
		File in = new File("src\\Text\\Video_Music_ListText.txt");
		try 
		{
			Scanner s = new Scanner(in);
			while (s.hasNextLine())
			{
				String line = s.nextLine();

	            StringTokenizer st = new StringTokenizer(line, "\t");
	            String [] Info = new String[10];
	            for (int i=0;i<10;i++)
	               Info[i]=st.nextToken();
	            Video_Music_List.add(new Video_Music(Info[0],Info[1],Info[2],Info[3],Info[4],Info[5],Info[6],Integer.parseInt(Info[7]),Integer.parseInt(Info[8]),Integer.parseInt(Info[9])));
			}
			s.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("���� "+in.getName()+"�� ã�� �� �����ϴ�.");
			System.exit(0);
		}
	}
	
	void WriteVideo_MusicInfo() {
		File out = new File("src\\Text\\Video_Music_ListText.txt");
		try
		{
			FileWriter w = new FileWriter(out);
			for (int i = 0; i < Video_Music_List.size(); i++)
			{
				Video_Music vm = Video_Music_List.get(i);
				String [] vm_L = vm.getMediaAllInfo();
				int [] vm_N = vm.getMediaNumInfo();
				for (int j =0; j<7;j++) {
					w.write(vm_L[j]+"\t");
				}
				for (int j=0;j<2;j++)
					w.write(Integer.toString(vm_N[j])+"\t");
				w.write(Integer.toString(vm_N[2])+"\n");
			}
			w.close();
		}
		catch (IOException e)
		{
			System.out.println("IOException ������ �߻��߽��ϴ�.");
			System.exit(0);
		}
	}
	
	void ReadNovel_ComicInfo() {
		File in = new File("src\\Text\\Novel_Comic_ListText.txt");
		try 
		{
			Scanner s = new Scanner(in);
			while (s.hasNextLine())
			{
				String line = s.nextLine();

	            StringTokenizer st = new StringTokenizer(line, "\t");
	            String [] Info = new String[9];
	            for (int i=0;i<9;i++)
	               Info[i]=st.nextToken();
	        
	            Novel_Comic_List.add(new Novel_Comic(Info[0],Info[1],Info[2],Info[3],Info[4],Info[5],Integer.parseInt(Info[6]),Integer.parseInt(Info[7]),Integer.parseInt(Info[8])));
			}
			s.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("���� "+in.getName()+"�� ã�� �� �����ϴ�.");
			System.exit(0);
		}
	}
	
	void WriteNovel_ComicInfo() {
		File out = new File("src\\Text\\Novel_Comic_ListText.txt");
		try
		{
			FileWriter w = new FileWriter(out);
			for (int i = 0; i < Novel_Comic_List.size(); i++)
			{
				Novel_Comic nc = Novel_Comic_List.get(i);
				String [] nc_L = nc.getMediaAllInfo();
				int [] nc_N = nc.getMediaNumInfo();
				for (int j =0; j<6;j++) {
					w.write(nc_L[j]+"\t");
				}
				for (int j=0;j<2;j++)
					w.write(Integer.toString(nc_N[j])+"\t");
				w.write(Integer.toString(nc_N[2])+"\n");
			}
			w.close();
		}
		catch (IOException e)
		{
			System.out.println("IOException ������ �߻��߽��ϴ�.");
			System.exit(0);
		}
	}
	
	
	void DelMediaInfo(String thumbnail, String type, String category, String donation, String time, String title, String id) {
		if (time.equals("Novel_Comic")) { //Novel_Comic
			String [] temp = {id, title, thumbnail, type, category, donation};
			for (int i=0;i<Novel_Comic_List.size();i++) {
				if (Arrays.equals(Novel_Comic_List.get(i).getMediaAllInfo(),temp)) {
					Novel_Comic_List.remove(i);
					break;
				}
			}
			WriteNovel_ComicInfo();
		}
		else { //Video_Music
			String [] temp = {id, title, thumbnail, type, category, donation, time};
			for (int i=0;i<Video_Music_List.size();i++) {
				if (Arrays.equals(Video_Music_List.get(i).getMediaAllInfo(),temp)) {
					Video_Music_List.remove(i);
					break;
				}
			}
			WriteVideo_MusicInfo();
		}		
		mediaCount = Video_Music_List.size()+Novel_Comic_List.size();
	}
	
	void ReadBookmark() {
		File in = new File("src\\Text\\Bookmark.txt");
		try 
		{
			Scanner s = new Scanner(in);
			while (s.hasNextLine())
			{
				String line = s.nextLine();
	            StringTokenizer st = new StringTokenizer(line, "\t");
	            String [] temp = new String[2];
	            for (int i=0;i<2;i++)
	               temp[i]=st.nextToken();
	            Vector<String> temp_user = new Vector<String>();
	            StringTokenizer st2 = new StringTokenizer(temp[1], " ");
            	
	            while(st2.hasMoreTokens()) {
	            	String a = st2.nextToken();
	            	temp_user.add(a);
	            }
	            Bookmark_List.add(new Bookmark(temp[0], temp_user));
			}
			s.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("���� "+in.getName()+"�� ã�� �� �����ϴ�.");
			System.exit(0);
		}
	}
	
	void WriteBookmark() {
		File out = new File("src\\Text\\Bookmark.txt");
		try
		{
			FileWriter w = new FileWriter(out);
			for (int i = 0; i < Bookmark_List.size(); i++)
			{
				Bookmark b = Bookmark_List.get(i);
				String book = b.getBook();
				w.write(book+"\t");
				Vector<String> book_user = b.getBookUser();
				for (int j=0;j<book_user.size();j++) {
					if (j==book_user.size()-1)
						w.write(book_user.get(j));
					else
						w.write(book_user.get(j)+" ");
				}
					w.write("\n");
			}
			w.close();
		}
		catch (IOException e)
		{
			System.out.println("IOException ������ �߻��߽��ϴ�.");
			System.exit(0);
		}
	}

	void DelBookmarkInfo(String thumbnail) { //���� ������
		for (int i=0; i<Bookmark_List.size();i++) {
			if (Bookmark_List.get(i).getBook().equals(thumbnail))
				Bookmark_List.remove(i);
		}
		WriteBookmark();
	}
	
	void DelBookmarkUser(String thumbnail, String id) { //������ �ϸ�ũ ������
		for (int i=0; i<Bookmark_List.size();i++) {
			if (Bookmark_List.get(i).getBook().equals(thumbnail)) {
				String temp_media = Bookmark_List.get(i).getBook();
				Vector<String> temp_user = Bookmark_List.get(i).getBookUser();
				for (int j=0; j<temp_user.size();j++) {
					if (temp_user.get(j).equals(id))
						temp_user.remove(j);
				}
				Bookmark_List.remove(i);
				Bookmark_List.add(new Bookmark(temp_media, temp_user));
			}
			WriteBookmark();
		}
	}
	
	void AddBookmarkUser(String thumbnail, String id) { //������ �ϸ�ũ �߰���
		int test = 0;
		for (int i=0; i<Bookmark_List.size();i++) {
			if (Bookmark_List.get(i).getBook().equals(thumbnail)) {
				String temp_media = Bookmark_List.get(i).getBook();
				Vector<String> temp_user = Bookmark_List.get(i).getBookUser();
				temp_user.add(id);
				Bookmark_List.remove(i);
				Bookmark_List.add(new Bookmark(temp_media, temp_user));
				test++;
				WriteBookmark();
				break;
			}
		}
	}
	
	void WriteEventLog(String event) {
		File f = new File("src\\Text\\EventLog.txt");
		try
		{
			Date today = new Date();
			SimpleDateFormat date = new SimpleDateFormat("yy/MM/dd");
			SimpleDateFormat time = new SimpleDateFormat("hh:mm a");
			FileWriter w = new FileWriter(f, true);
			w.write("["+date.format(today)+" "+time.format(today)+"] "+event+"\n");
			w.close();
		}
		catch (IOException e)
		{
			System.out.println("IOException ������ �߻��߽��ϴ�.");
			System.exit(0);
		}
	}
	
	void ReadImageCount() {
		File in = new File("src\\Text\\ImageCount.txt");
		try 
		{
			Scanner s = new Scanner(in);
			while (s.hasNextLine())
			{
				String line = s.nextLine();
				ImageCount = Integer.parseInt(line);
			}
			s.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("���� "+in.getName()+"�� ã�� �� �����ϴ�.");
			System.exit(0);
		}
	}
	
	void WriteImageCount() {
		File out = new File("src\\Text\\ImageCount.txt");
		try
		{
			FileWriter w = new FileWriter(out);
			w.write(Integer.toString(ImageCount));
			w.close();
		}
		catch (IOException e)
		{
			System.out.println("IOException ������ �߻��߽��ϴ�.");
			System.exit(0);
		}
	}
	
	String CopyThumbnail(String path, String format) { //�̹��� ���� �� ����
		try{
			ReadImageCount();
			ImageCount++;
			File file = new File(path);
			byte[] bytes = new byte[(int)file.length()];
			DataInputStream input = new DataInputStream(new FileInputStream(file));
			
			input.readFully(bytes);
			input.close();
			
			FileOutputStream output = new FileOutputStream("src/Ficture/"+Integer.toString(ImageCount)+"."+format);
			output.write(bytes);
			output.close();

			WriteImageCount();
		}
		catch (IOException e) {
			System.out.println(e);
		}
		return "src/Ficture/"+Integer.toString(ImageCount)+"."+format;
	}
	
	void updateMedia(String thumbnail, String type, String category, String donation, String time, String title, String id, int location, int donation_num, int good_num, int etc_num) { //time = Novel_Comic �̸� Novel_Comic
		if (location<60) { //�뽬���� �ִ� 60��
			ImageIcon thumbnail_�̵�� = new ImageIcon(thumbnail); //�����
			Image thumbnail_�̵��_img = thumbnail_�̵��.getImage();
			Image thumbnail_�̵��_re = thumbnail_�̵��_img.getScaledInstance(220, 115, Image.SCALE_SMOOTH);
			JButton thumbnail_�̵��_B = new JButton(new ImageIcon(thumbnail_�̵��_re));
			thumbnail_�̵��_B.setBorderPainted(false);
			thumbnail_�̵��_B.setContentAreaFilled(false);
			thumbnail_�̵��_B.setFocusPainted(false);
			thumbnail_�̵��_B.setBounds(10,10,220,115);
			thumbnail_�̵��_B.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (loginID==null)
						JOptionPane.showMessageDialog(null, "�α��� �� �̿��Ͻ� �� �ֽ��ϴ�.","Media_Info_Fail",JOptionPane.ERROR_MESSAGE);
					else {
						WriteEventLog(loginID+" �̵�� \""+title+"\" Ŭ��");
						media_Info_screen media_Info_frame = new media_Info_screen(thumbnail, type, category, donation, time, title, id, location, donation_num, good_num, etc_num);
						media_Info_frame.setVisible(true);
					}
				}
			});
			panel_�̵��[location].add(thumbnail_�̵��_B);
			
			ImageIcon [] icon_�̵�� = new ImageIcon[3];
			JLabel [] icon_�̵��_L = new JLabel[3];
		
			String [] icon_�̹���= GetImageInfo(type,category,donation);
			
			for (int i=0; i<3;i++) {
				icon_�̵��[i]=new ImageIcon(icon_�̹���[i]);			
				Image icon_�̵��_img = icon_�̵��[i].getImage();
				Image icon_�̵��_re = icon_�̵��_img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
				icon_�̵��_L[i]=new JLabel(new ImageIcon(icon_�̵��_re));
				icon_�̵��_L[i].setBounds(10+35*i,130,25,25);
				panel_�̵��[location].add(icon_�̵��_L[i]);
			}
			if (time.equals("Novel_Comic")) { //Novel_Comic
				JLabel [] text_�̵�� = new JLabel[2];
				text_�̵��[0] = new JLabel(title);
				text_�̵��[1] = new JLabel(id);
				for (int i=0;i<2;i++) {
					text_�̵��[i].setFont(new Font("���� ���", Font.PLAIN, 15));
				}
				text_�̵��[0].setBounds(10,165,230,30);
				text_�̵��[1].setBounds(10,190,230,30);
				for (int i=0;i<2;i++)
					panel_�̵��[location].add(text_�̵��[i]);
		
				if (checkBookmark(thumbnail, loginID)) {
					ImageIcon bookmark_�̵�� = new ImageIcon("src\\images\\bookmark.PNG"); //�ϸ�ũ
					Image bookmark_�̵��_img = bookmark_�̵��.getImage();
					Image bookmark_�̵��_re = bookmark_�̵��_img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
					JLabel bookmark_�̵��_L = new JLabel(new ImageIcon(bookmark_�̵��_re));
					bookmark_�̵��_L.setBounds(205,130,25,25);
					panel_�̵��[location].add(bookmark_�̵��_L);
				}
			}
			else { //Video_Music
				JLabel [] text_�̵�� = new JLabel[3]; 
				text_�̵��[0] = new JLabel(time);
				text_�̵��[1] = new JLabel(title);
				text_�̵��[2] = new JLabel(id);
				for (int i=0;i<3;i++)
					text_�̵��[i].setFont(new Font("���� ���", Font.PLAIN, 15));
				text_�̵��[0].setBounds(180,130,100,30);
				text_�̵��[1].setBounds(10,165,230,30);
				text_�̵��[2].setBounds(10,190,230,30);
				for (int i=0;i<3;i++)
					panel_�̵��[location].add(text_�̵��[i]);	
			}
			
			ImageIcon del_�̵�� = new ImageIcon("src\\images\\delete.PNG"); //����
			Image del_�̵��_img = del_�̵��.getImage();
			Image del_�̵��_re = del_�̵��_img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
			JButton del_�̵��_B = new JButton(new ImageIcon(del_�̵��_re));
			del_�̵��_B.setBorderPainted(false);
			del_�̵��_B.setContentAreaFilled(false);
			del_�̵��_B.setFocusPainted(false);
			del_�̵��_B.setBounds(200,190,25,25);
			del_�̵��_B.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int result = JOptionPane.showConfirmDialog(null, "���� �ش� �̵� �����Ͻðڽ��ϱ�?","delete",JOptionPane.YES_NO_OPTION);
					if (result==JOptionPane.YES_OPTION) {
						WriteEventLog(loginID+" �̵�� \""+title+"\" ����");
						DelMediaInfo(thumbnail, type, category, donation, time, title, id);
						DelBookmarkInfo(thumbnail);
						
						for (int i=0;i<60;i++)
							panel_�̵��[i].removeAll();
						panel_03.repaint();
						for (int i=0; i<Video_Music_List.size(); i++)
							updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
									Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],i,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
						for (int i=0; i<Novel_Comic_List.size();i++)
							updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
									"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],i+Video_Music_List.size(),Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
						
						JOptionPane.showMessageDialog(null, "�̵�� ������ �Ϸ�Ǿ����ϴ�.","Delete_Success",JOptionPane.INFORMATION_MESSAGE);
					}
					else if (result==JOptionPane.NO_OPTION) {
						JOptionPane.showMessageDialog(null, "�̵�� ������ ��ҵǾ����ϴ�!","Delete_Cancel",JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
			panel_�̵��[location].add(del_�̵��_B);

			if (loginID==null) {
				del_�̵��_B.setEnabled(false);
				del_�̵��_B.setVisible(false);
			}
			else {
				if (GetPosition(loginID).equals("������")) {
					del_�̵��_B.setEnabled(true);
					del_�̵��_B.setVisible(true);
				}
				else { //�����
					if (id.equals(loginID)) {
						del_�̵��_B.setEnabled(true);
						del_�̵��_B.setVisible(true);	
					}
					else {
						del_�̵��_B.setEnabled(false);
						del_�̵��_B.setVisible(false);
					}
				}
			}
			panel_03.repaint();
		}
	}
	
	//ȭ�� �ڵ� ���� ������ ����
	
	public screen() { //���� ȭ��
		
		ReadUserInfo();
		ReadVideo_MusicInfo();
		ReadNovel_ComicInfo();
		ReadBookmark();
		
		mediaCount = Video_Music_List.size()+Novel_Comic_List.size();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 150, 1000, 700);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JPanel panel_01 = new JPanel();
		panel_01.setBackground(new Color(255, 255, 255));
		panel_01.setBounds(0, 0, 985, 75);
		panel_01.setLayout(null);
		
		ȯ�� = new JLabel("");
		ȯ��.setFont(new Font("���� ���", Font.PLAIN, 15));
		ȯ��.setBounds(356, 13, 250, 15);
		panel_01.add(ȯ��);
		ȯ��.setEnabled(false);
		ȯ��.setVisible(false);

		�α��� = new JButton("\uB85C\uADF8\uC778"); //�α��� ��ư
		�α���.setFont(new Font("���� ���", Font.PLAIN, 15));
		�α���.setBackground(new Color(135, 206, 235));
		�α���.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login_screen login_frame = new login_screen();
				login_frame.setVisible(true);
			}
		});
		�α���.setBounds(850, 10, 113, 23);
		panel_01.add(�α���);
		
		�α׾ƿ� = new JButton("�α׾ƿ�"); //�α׾ƿ� ��ư
		�α׾ƿ�.setFont(new Font("���� ���", Font.PLAIN, 15));
		�α׾ƿ�.setBackground(new Color(135, 206, 235));
		�α׾ƿ�.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "�α׾ƿ��� �����ϼ̽��ϴ�.\n������ �� ������!","Logout_Success",JOptionPane.INFORMATION_MESSAGE);
				if (loginPosition.equals("������")) {
					WriteEventLog("������, "+loginID+" �α׾ƿ�");
					ȸ������.setEnabled(false);
					ȸ������.setVisible(false);
				}
				else if (loginPosition.equals("�����")) {
					WriteEventLog("�����, "+loginID+" �α׾ƿ�");
					ȸ��Ż��.setEnabled(false);
					ȸ��Ż��.setVisible(false);
				}
				loginID = null;
				
				loginPosition=null;
				�α���.setEnabled(true);
				�α���.setVisible(true);
				�α׾ƿ�.setEnabled(false);
				�α׾ƿ�.setVisible(false);
				ȸ������.setEnabled(true);
				ȸ������.setVisible(true);
				ȸ��Ż��.setEnabled(false);
				ȸ��Ż��.setVisible(false);
				ȯ��.setEnabled(false);
				ȯ��.setVisible(false);
				
				//���ΰ�ħ
				for (int i=0;i<60;i++)
					panel_�̵��[i].removeAll();
				panel_03.repaint();
				for (int i=0; i<Video_Music_List.size(); i++)
					updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
							Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],i,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
				for (int i=0; i<Novel_Comic_List.size();i++)
					updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
							"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],i+Video_Music_List.size(),Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
				SetButtonColor();
			}
		});
		�α׾ƿ�.setBounds(850, 10, 113, 23);
		panel_01.add(�α׾ƿ�);
		�α׾ƿ�.setEnabled(false);
		�α׾ƿ�.setVisible(false);
		
		ȸ������ = new JButton("\uD68C\uC6D0\uAC00\uC785"); //ȸ������ ��ư
		ȸ������.setFont(new Font("���� ���", Font.PLAIN, 15));
		ȸ������.setBackground(new Color(135, 206, 235));
		ȸ������.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sign_up_screen sign_up_frame = new sign_up_screen();
				sign_up_frame.setVisible(true);
			}
		});
		ȸ������.setBounds(850, 43, 113, 23);
		panel_01.add(ȸ������);
		
		ȸ��Ż�� = new JButton("ȸ��Ż��"); //ȸ��Ż�� ��ư
		ȸ��Ż��.setFont(new Font("���� ���", Font.PLAIN, 15));
		ȸ��Ż��.setBackground(new Color(135, 206, 235));
		ȸ��Ż��.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "���� ȸ��Ż�� �Ͻǰſ���?\n�ʹ� �ƽ�����.","sign_out",JOptionPane.YES_NO_OPTION);
				if (result==JOptionPane.YES_OPTION) {
					WriteEventLog(loginID+" ȸ��Ż��");
					
					DelUserInfo(loginID);
					loginID=null;
	
					�α���.setEnabled(true);
					�α���.setVisible(true);
					�α׾ƿ�.setEnabled(false);
					�α׾ƿ�.setVisible(false);
					ȸ������.setEnabled(true);
					ȸ������.setVisible(true);
					ȸ��Ż��.setEnabled(false);
					ȸ��Ż��.setVisible(false);
					ȯ��.setEnabled(false);
					ȯ��.setVisible(false);
					
					//���ΰ�ħ
					for (int i=0;i<60;i++)
						panel_�̵��[i].removeAll();
					panel_03.repaint();
					for (int i=0; i<Video_Music_List.size(); i++)
						updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
								Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],i,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
					for (int i=0; i<Novel_Comic_List.size();i++)
						updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
								"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],i+Video_Music_List.size(),Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
					SetButtonColor();
		
					JOptionPane.showMessageDialog(null, "ȸ��Ż�� �Ϸ�Ǿ����ϴ�.\n�ȳ��� ������!","Sign_out_Success",JOptionPane.INFORMATION_MESSAGE);
				}
				else if (result==JOptionPane.NO_OPTION) {
					JOptionPane.showMessageDialog(null, "ȸ��Ż�� ��ҵǾ����ϴ�!\n�� �����ϼ̾��!","Sign_out_Cancel",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		ȸ��Ż��.setBounds(850, 43, 113, 23);
		panel_01.add(ȸ��Ż��);
		ȸ��Ż��.setEnabled(false);
		ȸ��Ż��.setVisible(false);
		
		ȸ������ = new JButton("ȸ������"); //ȸ������ ��ư
		ȸ������.setFont(new Font("���� ���", Font.PLAIN, 15));
		ȸ������.setBackground(new Color(135, 206, 235));
		ȸ������.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manager_screen manager_frame = new manager_screen();
				manager_frame.setVisible(true);
			}
		});
		ȸ������.setBounds(850, 43, 113, 23);
		panel_01.add(ȸ������);
		ȸ������.setEnabled(false);
		ȸ������.setVisible(false);
		
		JButton �̵���� = new JButton("\uBBF8\uB514\uC5B4 \uB4F1\uB85D"); //�̵�� ��� ��ư
		�̵����.setFont(new Font("���� ���", Font.PLAIN, 15));
		�̵����.setBackground(new Color(135, 206, 235));
		�̵����.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (loginID==null)
					JOptionPane.showMessageDialog(null, "�α��� �� �̿��Ͻ� �� �ֽ��ϴ�.","Upload_Fail",JOptionPane.ERROR_MESSAGE);
				else {
					upload_screen upload_frame = new upload_screen();
					upload_frame.setVisible(true);
				}
			}
		});
		�̵����.setBounds(715, 10, 113, 23);
		panel_01.add(�̵����);
		
		�˻�â = new JTextField();
		�˻�â.setFont(new Font("���� ���", Font.PLAIN, 15));
		�˻�â.setForeground(new Color(0, 0, 0));
		�˻�â.setBounds(355, 35, 250, 30);
		panel_01.add(�˻�â);
		�˻�â.setColumns(10);
		
		JPanel panel_02 = new JPanel();
		panel_02.setBackground(new Color(255, 255, 255));
		panel_02.setBounds(0, 75, 985, 125);
		panel_02.setLayout(new GridLayout(1,2,3,0));
		contentPane.add(panel_02);
		
		JPanel panel_02_01 = new JPanel();
		panel_02_01.setBackground(new Color(135, 206, 235));
		panel_02.add(panel_02_01);
		panel_02_01.setLayout(new GridLayout(2,1,0,0));
		
		JPanel ����_panel = new JPanel();
		����_panel.setBackground(new Color(135, 206, 235));
		panel_02_01.add(����_panel);
		����_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel ���� = new JLabel("   \uC720\uD615");
		����.setFont(new Font("���� ���", Font.PLAIN, 30));
		����_panel.add(����);
		
		JPanel ����_panel_2 = new JPanel();
		����_panel_2.setBackground(new Color(135, 206, 235));
		panel_02_01.add(����_panel_2);
		����_panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		for (int i = 0;i<����_btn_name.length;i++) {
			����_btn[i] = new JButton(����_btn_name[i]);
			����_btn[i].setFont(new Font("���� ���", Font.PLAIN, 15));
			����_btn[i].setBackground(Color.WHITE);
			����_btn[i].setPreferredSize(new Dimension(85,40));
			����_btn[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton b = (JButton)e.getSource();
					for (int i=0;i<����_btn_name.length;i++) {
						����_btn[i].setForeground(new Color(0, 0, 0));
						����_btn[i].setBackground(Color.white);
					}
					b.setForeground(new Color(255,0,0));
					b.setBackground(SystemColor.controlHighlight);
					
					Selected_Type=b.getText();
					
					for (int i=0;i<60;i++)
						panel_�̵��[i].removeAll();
					panel_03.repaint();
					switch(Selected_Type) {
					case "��ü":
						if (Selected_Category.equals("��ü")) { //���� ��ü, ī�װ� ��ü
							for (int i=0; i<Video_Music_List.size(); i++)
								updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
										Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],i,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
							for (int i=0; i<Novel_Comic_List.size();i++)
								updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
										"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],i+Video_Music_List.size(),Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
						}
						else { //���� ��ü, ī�װ� ����
							int temp = 0;
							for (int i=0; i<Video_Music_List.size(); i++) {
								if (Video_Music_List.get(i).getMediaAllInfo()[4].equals(Selected_Category)) {
									updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
											Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],temp,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
									temp++;
								}
							}
							for (int i=0; i<Novel_Comic_List.size();i++) {
								if (Novel_Comic_List.get(i).getMediaAllInfo()[4].equals(Selected_Category)) {
									updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
											"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],temp,Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
									temp++;
								}
							}
						}
						break;
					case "��ȭ" : case "�Ҽ�" : case "����" : case "������" :
						if (Selected_Category.equals("��ü")) { //���� ����, ī�װ� ��ü
							int temp = 0;
							for (int i=0; i<Video_Music_List.size(); i++) {
								if (Video_Music_List.get(i).getMediaAllInfo()[3].equals(Selected_Type)) {
									updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
											Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],temp,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
									temp++;
								}
							}
							for (int i=0; i<Novel_Comic_List.size();i++) {
								if (Novel_Comic_List.get(i).getMediaAllInfo()[3].equals(Selected_Type)) {
									updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
											"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],temp,Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
									temp++;
								}
							}
						}
						else { //���� ����, ī�װ� ����
							int temp = 0;
							for (int i=0; i<Video_Music_List.size(); i++) {
								if (Video_Music_List.get(i).getMediaAllInfo()[3].equals(Selected_Type)&&Video_Music_List.get(i).getMediaAllInfo()[4].equals(Selected_Category)) {
									updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
											Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],temp,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
									temp++;
								}
							}
							for (int i=0; i<Novel_Comic_List.size();i++) {
								if (Novel_Comic_List.get(i).getMediaAllInfo()[3].equals(Selected_Type)&&Novel_Comic_List.get(i).getMediaAllInfo()[4].equals(Selected_Category)) {
									updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
											"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],temp,Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
									temp++;
								}
							}
						}
						break;
					}
				}
			});
			����_panel_2.add(����_btn[i]);
		}
		����_btn[0].setForeground(new Color(255, 0, 0));
		����_btn[0].setBackground(SystemColor.controlHighlight);
		
		JPanel panel_02_02 = new JPanel();
		panel_02_02.setBackground(new Color(135, 206, 235));
		panel_02.add(panel_02_02);
		panel_02_02.setLayout(new GridLayout(2,1,0,0));
		
		JPanel ī�װ�_panel = new JPanel();
		ī�װ�_panel.setBackground(new Color(135, 206, 235));
		panel_02_02.add(ī�װ�_panel);
		ī�װ�_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel ī�װ� = new JLabel("   \uCE74\uD14C\uACE0\uB9AC");
		ī�װ�_panel.add(ī�װ�);
		ī�װ�.setFont(new Font("���� ���", Font.PLAIN, 30));
		
		JPanel ī�װ�_panel_2 = new JPanel();
		ī�װ�_panel_2.setBackground(new Color(135, 206, 235));
		panel_02_02.add(ī�װ�_panel_2);
		
		for (int i = 0;i<ī�װ�_btn_name.length;i++) {
			ī�װ�_btn[i] = new JButton(ī�װ�_btn_name[i]);
			ī�װ�_btn[i].setFont(new Font("���� ���", Font.PLAIN, 15));
			ī�װ�_btn[i].setBackground(Color.WHITE);
			ī�װ�_btn[i].setPreferredSize(new Dimension(85,40));
			ī�װ�_btn[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton b = (JButton)e.getSource();
					for (int i=0;i<ī�װ�_btn_name.length;i++) {
						ī�װ�_btn[i].setForeground(new Color(0, 0, 0));
						ī�װ�_btn[i].setBackground(Color.white);
					}
					b.setForeground(new Color(255,0,0));
					b.setBackground(SystemColor.controlHighlight);
					
					Selected_Category=b.getText();
					for (int i=0;i<60;i++)
						panel_�̵��[i].removeAll();
					panel_03.repaint();
					switch(Selected_Type) {
					case "��ü":
						if (Selected_Category.equals("��ü")) { //���� ��ü, ī�װ� ��ü
							for (int i=0; i<Video_Music_List.size(); i++)
								updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
										Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],i,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
							for (int i=0; i<Novel_Comic_List.size();i++)
								updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
										"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],i+Video_Music_List.size(),Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
						}
						else { //���� ��ü, ī�װ� ����
							int temp = 0;
							for (int i=0; i<Video_Music_List.size(); i++) {
								if (Video_Music_List.get(i).getMediaAllInfo()[4].equals(Selected_Category)) {
									updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
											Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],temp,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
									temp++;
								}
							}
							for (int i=0; i<Novel_Comic_List.size();i++) {
								if (Novel_Comic_List.get(i).getMediaAllInfo()[4].equals(Selected_Category)) {
									updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
											"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],temp,Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
									temp++;
								}
							}
						}
						break;
					case "��ȭ" : case "�Ҽ�" : case "����" : case "������" :
						if (Selected_Category.equals("��ü")) { //���� ����, ī�װ� ��ü
							int temp = 0;
							for (int i=0; i<Video_Music_List.size(); i++) {
								if (Video_Music_List.get(i).getMediaAllInfo()[3].equals(Selected_Type)) {
									updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
											Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],temp,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
									temp++;
								}
							}
							for (int i=0; i<Novel_Comic_List.size();i++) {
								if (Novel_Comic_List.get(i).getMediaAllInfo()[3].equals(Selected_Type)) {
									updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
											"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],temp,Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
									temp++;
								}
							}
						}
						else { //���� ����, ī�װ� ����
							int temp = 0;
							for (int i=0; i<Video_Music_List.size(); i++) {
								if (Video_Music_List.get(i).getMediaAllInfo()[3].equals(Selected_Type)&&Video_Music_List.get(i).getMediaAllInfo()[4].equals(Selected_Category)) {
									updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
											Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],temp,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
									temp++;
								}
							}
							for (int i=0; i<Novel_Comic_List.size();i++) {
								if (Novel_Comic_List.get(i).getMediaAllInfo()[3].equals(Selected_Type)&&Novel_Comic_List.get(i).getMediaAllInfo()[4].equals(Selected_Category)) {
									updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
											"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],temp,Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
									temp++;
								}
							}
						}
						break;
					}
				}
			});
			ī�װ�_panel_2.add(ī�װ�_btn[i]);
		}
		ī�װ�_btn[0].setForeground(new Color(255, 0, 0));
		ī�װ�_btn[0].setBackground(SystemColor.controlHighlight);
	
		ImageIcon logo = new ImageIcon("src\\images\\logo.PNG"); //�ΰ�
		JButton logo_B = new JButton(logo);
		logo_B.setBorderPainted(false);
		logo_B.setContentAreaFilled(false);
		logo_B.setFocusPainted(false);
		logo_B.setBounds(20,10,300,55);
		logo_B.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i=0;i<60;i++)
					panel_�̵��[i].removeAll();
				panel_03.repaint();
				for (int i=0; i<Video_Music_List.size(); i++)
					updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
							Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],i,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
				for (int i=0; i<Novel_Comic_List.size();i++)
					updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
							"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],i+Video_Music_List.size(),Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
				SetButtonColor();
			}
		});
		panel_01.add(logo_B);
		contentPane.add(panel_01);
		
		ImageIcon search = new ImageIcon("src\\images\\glass.png"); //������
		Image search_img = search.getImage();
		Image search_re = search_img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		JButton search_B = new JButton(new ImageIcon(search_re));
		search_B.setBorderPainted(false);
		search_B.setContentAreaFilled(false);
		search_B.setFocusPainted(false);
		search_B.setBounds(618,35,25,25);
		search_B.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (�˻�â.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "�˻�� �Է����ּ���.","Search_Fail",JOptionPane.ERROR_MESSAGE);
				else if (NotExistTitle(�˻�â.getText())) //�ش� �˻���� ��ġ�ϴ� �̵� ���� ���
					JOptionPane.showMessageDialog(null, "�˻��� ["+�˻�â.getText()+"] �� �ش��ϴ� �˻� ����� �����ϴ�.\n�ٸ� �˻�� �Է����ּ���.","Search_Fail",JOptionPane.ERROR_MESSAGE);
				else {				
					int temp = 0;
					for (int i=0;i<60;i++)
						panel_�̵��[i].removeAll();
					panel_03.repaint();
					for (int i=0; i<Video_Music_List.size(); i++) {
						if (Video_Music_List.get(i).getMediaAllInfo()[1].contains(�˻�â.getText())) {
							updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
									Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],temp,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
							temp++;
						}
					}
					for (int i=0; i<Novel_Comic_List.size();i++) {
						if (Novel_Comic_List.get(i).getMediaAllInfo()[1].contains(�˻�â.getText())) {
							updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
									"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],temp,Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
							temp++;
						}
					}
					SetButtonColor();
					JOptionPane.showMessageDialog(null, "�˻��� ["+�˻�â.getText()+"] �˻� ����Դϴ�.","Search_Success",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		panel_01.add(search_B);
		
		JButton ���̵�� = new JButton("�� �̵��"); //�� �̵�� ��ư
		���̵��.setFont(new Font("���� ���", Font.PLAIN, 15));
		���̵��.setBackground(new Color(135, 206, 235));
		���̵��.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (loginID==null)
					JOptionPane.showMessageDialog(null, "�α��� �� �̿��Ͻ� �� �ֽ��ϴ�.","My_media_Fail",JOptionPane.ERROR_MESSAGE);
				else if (NotExistMyMedia(loginID))
					JOptionPane.showMessageDialog(null, "��ϵ� �� �̵� �����ϴ�.\n���ο� �̵� ������ּ���.","My_media_Fail",JOptionPane.ERROR_MESSAGE);
				else {
					int temp = 0;
					for (int i=0;i<60;i++)
						panel_�̵��[i].removeAll();
					for (int i=0; i<Video_Music_List.size(); i++) {
						if (Video_Music_List.get(i).getMediaAllInfo()[0].equals(loginID)) {
							updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
									Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],temp,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
							temp++;
						}
					}
					for (int i=0; i<Novel_Comic_List.size();i++) {
						if (Novel_Comic_List.get(i).getMediaAllInfo()[0].equals(loginID)) {
							updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
									"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],temp,Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
							temp++;
						}
					}
					SetButtonColor();
					JOptionPane.showMessageDialog(null, "�� �̵�� �˻� ����Դϴ�.","My_media_Success",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		���̵��.setBounds(715, 43, 113, 23);
		panel_01.add(���̵��);
		
		//�̵�� Panel
		for (int i =0;i<60;i++) {
			panel_�̵��[i] = new JPanel();
			panel_�̵��[i].setBackground(new Color(135, 206, 235));
			panel_�̵��[i].setPreferredSize(new Dimension(200,230));
			panel_03.add(panel_�̵��[i]);
			panel_�̵��[i].setLayout(null);
			panel_�̵��[i].setMaximumSize(getPreferredSize());
			panel_�̵��[i].setMinimumSize(getPreferredSize());
		}

		panel_03.setBackground(new Color(255, 255, 255));
		panel_03.setBounds(0, 203, 985, 460);
		panel_03.setLayout(new GridLayout(15,4,3,3));

		//��ϵ� �̵�� ����
		for (int i=0; i<Video_Music_List.size(); i++)
			updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
					Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],i,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
		
		for (int i=0; i<Novel_Comic_List.size();i++)
			updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
					"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],i+Video_Music_List.size(),Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
		
		JScrollPane scroll = new JScrollPane(panel_03);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(0,203,985,460);
		contentPane.add(scroll);
	}

	private class sign_up_screen extends JFrame{ //ȸ������ ȭ��
		public sign_up_screen() {
			setBounds(800, 200, 350, 550);
			setResizable(false);
			contentPane2 = new JPanel();
			contentPane2.setBackground(new Color(255, 255, 255));
			contentPane2.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane2.setLayout(null);
			setContentPane(contentPane2);
			
			name_input = new JTextField();
			name_input.setFont(new Font("���� ���", Font.PLAIN, 15));
			name_input.setBounds(34, 127, 262, 29);
			contentPane2.add(name_input);
			name_input.setColumns(10);
			
			JLabel name = new JLabel("\uC774\uB984");
			name.setFont(new Font("���� ���", Font.PLAIN, 15));
			name.setBounds(34, 102, 57, 15);
			contentPane2.add(name);
			
			id_input = new JTextField();
			id_input.setFont(new Font("���� ���", Font.PLAIN, 15));
			id_input.setColumns(10);
			id_input.setBounds(34, 205, 262, 29);
			contentPane2.add(id_input);
			
			JLabel id = new JLabel("ID");
			id.setFont(new Font("���� ���", Font.PLAIN, 15));
			id.setBounds(34, 180, 57, 15);
			contentPane2.add(id);
			
			pw_input = new JPasswordField();
			pw_input.setFont(new Font("���� ���", Font.PLAIN, 15));
			pw_input.setColumns(10);
			pw_input.setBounds(34, 283, 262, 29);
			contentPane2.add(pw_input);
			
			JLabel pw = new JLabel("PW");
			pw.setFont(new Font("���� ���", Font.PLAIN, 15));
			pw.setBounds(34, 258, 57, 15);
			contentPane2.add(pw);
			
			JLabel position = new JLabel("\uC720\uD615");
			position.setFont(new Font("���� ���", Font.PLAIN, 15));
			position.setBounds(34, 331, 57, 15);
			contentPane2.add(position);
			
			JComboBox position_input = new JComboBox();
			position_input.setBackground(new Color(255, 255, 255));
			position_input.setModel(new DefaultComboBoxModel(new String[] {"\uC0AC\uC6A9\uC790", "\uAD00\uB9AC\uC790"}));
			position_input.setFont(new Font("���� ���", Font.PLAIN, 15));
			position_input.setBounds(34, 356, 262, 29);
			contentPane2.add(position_input);
			
			JButton ��� = new JButton("\uCDE8\uC18C");
			���.setBackground(new Color(135, 206, 235));
			���.setFont(new Font("���� ���", Font.PLAIN, 15));
			���.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			���.setBounds(34, 430, 108, 38);
			contentPane2.add(���);
			
			JButton Ȯ�� = new JButton("\uD655\uC778");
			Ȯ��.setBackground(new Color(135, 206, 235));
			Ȯ��.setFont(new Font("���� ���", Font.PLAIN, 15));
			Ȯ��.setBounds(188, 430, 108, 38);
			Ȯ��.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (name_input.getText().isEmpty()||id_input.getText().isEmpty()||pw_input.getText().isEmpty())
						JOptionPane.showMessageDialog(null, "�Էµ��� ���� �׸��� �ֽ��ϴ�.\n�ٽ� Ȯ�����ּ���.","Sign_up_Fail",JOptionPane.ERROR_MESSAGE);
					else {
						if(id_input.getText().equals("."))
							JOptionPane.showMessageDialog(null, "����� �� ���� ID�Դϴ�.\n�ٽ� �Է����ּ���.","Sign_up_Fail",JOptionPane.ERROR_MESSAGE);
						else if (NotExistUserID(id_input.getText())){
							if (position_input.getSelectedItem().toString().equals("������")) { //�������� ��� ��� ��ȣ Ȯ��
								String manager_input_PW = JOptionPane.showInputDialog("������ ��� ��ȣ�� �Է��ϼ���.");
								if (manager_input_PW==null)
									dispose();
								else if (manager_input_PW.equals(manager_PW)) { //��� ��ȣ ��ġ
									WriteEventLog("������, "+id_input.getText()+" ȸ������");
									UserList.add(new UserInfo(name_input.getText(),id_input.getText(),pw_input.getText(),position_input.getSelectedItem().toString()));
									JOptionPane.showMessageDialog(null, "ȸ�����Կ� �����ϼ̽��ϴ�.\n���ϵ帳�ϴ�!","Sign_up_Success",JOptionPane.INFORMATION_MESSAGE);
									WriteUserInfo();
									dispose();
								}
								else //��� ��ȣ ����ġ
									JOptionPane.showMessageDialog(null, "������ ��� ��ȣ�� Ʋ�Ƚ��ϴ�.\n�ٽ� Ȯ�����ּ���.","Sign_up_Fail",JOptionPane.ERROR_MESSAGE);
							}
							else { //����� ����� ���
								WriteEventLog("�����, "+id_input.getText()+" ȸ������");
								UserList.add(new UserInfo(name_input.getText(),id_input.getText(),pw_input.getText(),position_input.getSelectedItem().toString()));
								JOptionPane.showMessageDialog(null, "ȸ�����Կ� �����ϼ̽��ϴ�.\n���ϵ帳�ϴ�!","Sign_up_Success",JOptionPane.INFORMATION_MESSAGE);
								WriteUserInfo();
								dispose();
							}
						}		
						else
							JOptionPane.showMessageDialog(null, "�̹� �����ϴ� ID�Դϴ�.\n�ٽ� �Է����ּ���.","Sign_up_Fail",JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			contentPane2.add(Ȯ��);
			
			ImageIcon logo = new ImageIcon("src\\images\\sign_up_logo.PNG"); //�ΰ�
			Image logo_img = logo.getImage();
			Image logo_re = logo_img.getScaledInstance(280, 80, Image.SCALE_SMOOTH);
			JLabel logo_L = new JLabel(new ImageIcon(logo_re));
			logo_L.setBounds(30,10,280,80);
			contentPane2.add(logo_L);
		}
	}
	
	private class login_screen extends JFrame { //�α��� ȭ��
		public login_screen() {
			setBounds(800, 200, 350, 550);
			setResizable(false);
			contentPane3 = new JPanel();
			contentPane3.setBackground(new Color(255, 255, 255));
			contentPane3.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane3.setLayout(null);
			setContentPane(contentPane3);
			
			ID_input = new JTextField();
			ID_input.setFont(new Font("���� ���", Font.PLAIN, 15));
			ID_input.setColumns(10);
			ID_input.setBounds(34, 185, 262, 29);
			contentPane3.add(ID_input);
			
			JLabel ID = new JLabel("ID");
			ID.setFont(new Font("���� ���", Font.PLAIN, 15));
			ID.setBounds(34, 160, 57, 15);
			contentPane3.add(ID);
			
			PW_input = new JPasswordField();
			PW_input.setFont(new Font("���� ���", Font.PLAIN, 15));
			PW_input.setColumns(10);
			PW_input.setBounds(34, 282, 262, 29);
			contentPane3.add(PW_input);
			
			JLabel PW = new JLabel("PW");
			PW.setFont(new Font("���� ���", Font.PLAIN, 15));
			PW.setBounds(34, 257, 57, 15);
			contentPane3.add(PW);
			
			JButton ��� = new JButton("\uCDE8\uC18C");
			���.setBackground(new Color(135, 206, 235));
			���.setFont(new Font("���� ���", Font.PLAIN, 15));
			���.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			���.setBounds(34, 430, 108, 38);
			contentPane3.add(���);
			
			JButton Ȯ�� = new JButton("\uD655\uC778");
			Ȯ��.setBackground(new Color(135, 206, 235));
			Ȯ��.setFont(new Font("���� ���", Font.PLAIN, 15));
			Ȯ��.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (ID_input.getText().isEmpty()||PW_input.getText().isEmpty())
						JOptionPane.showMessageDialog(null, "�Էµ��� ���� �׸��� �ֽ��ϴ�.\n�ٽ� Ȯ�����ּ���.","Login_Fail",JOptionPane.ERROR_MESSAGE);
					else {
						if (NotExistUserID(ID_input.getText())){ //���� ���̵� �Է�
							JOptionPane.showMessageDialog(null, "�������� �ʴ� ID�Դϴ�.\n�ٽ� �Է����ּ���.","Login_Fail",JOptionPane.ERROR_MESSAGE);
						}
						else if (CheckUserPW(ID_input.getText(),PW_input.getText())) { //�α��� ����
							loginID=ID_input.getText();
							loginPosition=GetPosition(loginID);
							JOptionPane.showMessageDialog(null, "�α��ο� �����ϼ̽��ϴ�.\n���ϵ帳�ϴ�!","Login_Success",JOptionPane.INFORMATION_MESSAGE);
							�α���.setEnabled(false);
							�α���.setVisible(false);
							�α׾ƿ�.setEnabled(true);
							�α׾ƿ�.setVisible(true);
							ȸ������.setEnabled(false);
							ȸ������.setVisible(false);
							ȯ��.setText(loginPosition+", "+loginID+"�� ȯ���մϴ�!");
							ȯ��.setEnabled(true);
							ȯ��.setVisible(true);
							if (loginPosition.equals("������")) {
								WriteEventLog("������, "+ID_input.getText()+" �α���");
								ȸ������.setEnabled(true);
								ȸ������.setVisible(true);
							}
							else if (loginPosition.equals("�����")) {
								WriteEventLog("�����, "+ID_input.getText()+" �α���");
								ȸ��Ż��.setEnabled(true);
								ȸ��Ż��.setVisible(true);
							}
							
							//���ΰ�ħ
							for (int i=0;i<60;i++)
								panel_�̵��[i].removeAll();
							panel_03.repaint();
							for (int i=0; i<Video_Music_List.size(); i++)
								updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
										Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],i,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
							for (int i=0; i<Novel_Comic_List.size();i++)
								updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
										"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],i+Video_Music_List.size(),Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
							SetButtonColor();
							dispose();
						}
						else //Ʋ�� ��й�ȣ �Է�
							JOptionPane.showMessageDialog(null, "��ġ���� �ʴ� PW�Դϴ�. \n�ٽ� �Է����ּ���.","Login_Fail",JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			Ȯ��.setBounds(188, 430, 108, 38);
			contentPane3.add(Ȯ��);
			
			ImageIcon logo = new ImageIcon("src\\images\\login_logo.PNG"); //�ΰ�
			Image logo_img = logo.getImage();
			Image logo_re = logo_img.getScaledInstance(280, 80, Image.SCALE_SMOOTH);
			JLabel logo_L = new JLabel(new ImageIcon(logo_re));
			logo_L.setBounds(30,10,280,80);
			contentPane3.add(logo_L);
		}
	}
	
	private class upload_screen extends JFrame{ //�̵�� ��� ȭ��
		public upload_screen() {
			setBounds(650, 300, 650, 450);
			setResizable(false);
			contentPane4 = new JPanel();
			contentPane4.setBackground(Color.WHITE);
			contentPane4.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane4.setLayout(null);
			setContentPane(contentPane4);
			
			JLabel ���� = new JLabel("\uBBF8\uB514\uC5B4 \uC81C\uBAA9");
			����.setFont(new Font("���� ���", Font.PLAIN, 15));
			����.setBounds(60, 102, 81, 15);
			contentPane4.add(����);
			
			title_input = new JTextField();
			title_input.setFont(new Font("���� ���", Font.PLAIN, 15));
			title_input.setBounds(60, 127, 196, 21);
			contentPane4.add(title_input);
			title_input.setColumns(10);
			
			ImageIcon logo = new ImageIcon("src\\images\\upload_logo.PNG"); //�ΰ�
			Image logo_img = logo.getImage();
			Image logo_re = logo_img.getScaledInstance(280, 80, Image.SCALE_SMOOTH);
			JLabel logo_L = new JLabel(new ImageIcon(logo_re));
			logo_L.setBounds(175,10,280,80);
			contentPane4.add(logo_L);
			
			JLabel �̹��� = new JLabel("\uB300\uD45C \uC774\uBBF8\uC9C0");
			�̹���.setFont(new Font("���� ���", Font.PLAIN, 15));
			�̹���.setBounds(60, 177, 81, 15);
			contentPane4.add(�̹���);
			
			Image_fopen = new FileDialog(this, "���� ����", FileDialog.LOAD);
			
			JButton file_open = new JButton("���� ����");
			file_open.setFont(new Font("���� ���", Font.PLAIN, 15));
			file_open.setBackground(SystemColor.controlHighlight);
			file_open.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Image_fopen.setVisible(true);
					thumbnail_input.setText(Image_fopen.getDirectory()+Image_fopen.getFile());
					Image_fopen_name=Image_fopen.getFile();
				}
			});
			file_open.setBounds(160, 177, 95, 17);
			contentPane4.add(file_open);
			
			thumbnail_input = new JTextField();
			thumbnail_input.setFont(new Font("���� ���", Font.PLAIN, 15));
			thumbnail_input.setColumns(10);
			thumbnail_input.setBounds(60, 202, 196, 21);
			contentPane4.add(thumbnail_input);
			
			JLabel ���� = new JLabel("\uC720\uD615");
			����.setFont(new Font("���� ���", Font.PLAIN, 15));
			����.setBounds(60, 249, 81, 15);
			contentPane4.add(����);
			
			JComboBox type_input = new JComboBox();
			type_input.setModel(new DefaultComboBoxModel(new String[] {"\uB3D9\uC601\uC0C1", "\uC74C\uC545", "\uC18C\uC124", "\uB9CC\uD654"}));
			type_input.setFont(new Font("���� ���", Font.PLAIN, 15));
			type_input.setBounds(60, 274, 196, 23);
			contentPane4.add(type_input);
			
			JComboBox category_input = new JComboBox();
			category_input.setModel(new DefaultComboBoxModel(new String[] {"\uAC8C\uC784", "\uC2A4\uD3EC\uCE20", "\uD559\uC2B5", "\uC74C\uC2DD"}));
			category_input.setFont(new Font("���� ���", Font.PLAIN, 15));
			category_input.setBounds(362, 125, 196, 23);
			contentPane4.add(category_input);
			
			JLabel ī�װ� = new JLabel("\uCE74\uD14C\uACE0\uB9AC");
			ī�װ�.setFont(new Font("���� ���", Font.PLAIN, 15));
			ī�װ�.setBounds(362, 100, 81, 15);
			contentPane4.add(ī�װ�);
			
			JComboBox donation_input = new JComboBox();
			donation_input.setModel(new DefaultComboBoxModel(new String[] {"\uAC00\uB2A5", "\uBD88\uAC00\uB2A5"}));
			donation_input.setFont(new Font("���� ���", Font.PLAIN, 15));
			donation_input.setBounds(362, 202, 196, 23);
			contentPane4.add(donation_input);
			
			JLabel ��� = new JLabel("\uAE30\uBD80");
			���.setFont(new Font("���� ���", Font.PLAIN, 15));
			���.setBounds(362, 177, 81, 15);
			contentPane4.add(���);
			
			JSpinner time_input_01 = new JSpinner();
			time_input_01.setModel(new SpinnerNumberModel(0, 0, 59, 1));
			time_input_01.setFont(new Font("���� ���", Font.PLAIN, 15));
			time_input_01.setBounds(430, 276, 56, 22);
			contentPane4.add(time_input_01);
			
			JSpinner time_input_02 = new JSpinner();
			time_input_02.setModel(new SpinnerNumberModel(0, 0, 59, 1));
			time_input_02.setFont(new Font("���� ���", Font.PLAIN, 15));
			time_input_02.setBounds(362, 276, 56, 22);
			contentPane4.add(time_input_02);
			
			JLabel �ð� = new JLabel("\uC7AC\uC0DD \uC2DC\uAC04");
			�ð�.setFont(new Font("���� ���", Font.PLAIN, 15));
			�ð�.setBounds(362, 250, 81, 15);
			contentPane4.add(�ð�);
			
			JButton ��� = new JButton("\uCDE8\uC18C");
			���.setFont(new Font("���� ���", Font.PLAIN, 15));
			���.setBackground(new Color(135, 206, 235));
			���.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			���.setBounds(150, 342, 108, 38);
			contentPane4.add(���);
			
			JButton ��� = new JButton("\uB4F1\uB85D");
			���.setFont(new Font("���� ���", Font.PLAIN, 15));
			���.setBackground(new Color(135, 206, 235));
			���.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (title_input.getText().isEmpty()||thumbnail_input.getText().isEmpty()) //����ִ� ���� ���� ���
						JOptionPane.showMessageDialog(null, "�Էµ��� ���� �׸��� �ֽ��ϴ�.\n�ٽ� Ȯ�����ּ���.","Upload_Fail",JOptionPane.ERROR_MESSAGE);
					else if (CheckTumbnail(thumbnail_input.getText())==false) //�Է� ������ �̹����� �ƴ� ���
						JOptionPane.showMessageDialog(null, "��ǥ �̹��� �Է��� �߸��ƽ��ϴ�.\n�̹��� ������ �Է����ּ���.","Upload_Fail",JOptionPane.ERROR_MESSAGE);
					else { 
						if (type_input.getSelectedItem().toString().equals("������")||type_input.getSelectedItem().toString().equals("����")) { //Video_Music
							if (time_input_01.getValue().toString().equals("0")&&time_input_02.getValue().toString().equals("0")) //����ð��� �Էµ��� �ʾ��� ���
								JOptionPane.showMessageDialog(null, "��� �ð��� �Էµ��� �ʾҽ��ϴ�.\n��� �ð��� �Է����ּ���.","Upload_Fail",JOptionPane.ERROR_MESSAGE);
							else {
								String time = CheckTime(time_input_01.getValue().toString(), time_input_02.getValue().toString());
								String file_name = Image_fopen_name;
								int pos = file_name.lastIndexOf(".");
								String temp = file_name.substring(pos+1);
								String thumbnail = CopyThumbnail(thumbnail_input.getText(), temp);
								if (ExistMedia(thumbnail,type_input.getSelectedItem().toString(), category_input.getSelectedItem().toString(),donation_input.getSelectedItem().toString(),time,title_input.getText(),loginID)) //�̹� �����ϴ� �̵��
									JOptionPane.showMessageDialog(null, "�̹� ��ϵ� �̵���Դϴ�.\n�ٽ� Ȯ�����ּ���.","Upload_Fail",JOptionPane.ERROR_MESSAGE);
								else { //����
									WriteEventLog(loginID+" �̵�� \""+title_input.getText()+"\" ���");
									Video_Music_List.add(new Video_Music(loginID,title_input.getText(),thumbnail,type_input.getSelectedItem().toString(),category_input.getSelectedItem().toString(),
											donation_input.getSelectedItem().toString(),time,0,0,0));
									mediaCount+=1;
									
									//�̵�� ���� �Լ�
									updateMedia(thumbnail, type_input.getSelectedItem().toString(),category_input.getSelectedItem().toString(),donation_input.getSelectedItem().toString(), time,
											title_input.getText(), loginID, mediaCount-1,0,0,0);
									
									JOptionPane.showMessageDialog(null, "�̵�� ��Ͽ� �����ϼ̽��ϴ�.\n���ϵ帳�ϴ�!","Sign_up_Success",JOptionPane.INFORMATION_MESSAGE);
									WriteVideo_MusicInfo();
									dispose();
								}
							}
						}
						else { //Novel_Comic
							String file_name = Image_fopen_name;
							int pos = file_name.lastIndexOf(".");
							String temp = file_name.substring(pos+1);
							String thumbnail = CopyThumbnail(thumbnail_input.getText(), temp);
							if (ExistMedia(thumbnail,type_input.getSelectedItem().toString(), category_input.getSelectedItem().toString(),donation_input.getSelectedItem().toString(),"Novel_Comic",title_input.getText(),loginID)) //�̹� �����ϴ� �̵��
								JOptionPane.showMessageDialog(null, "�̹� ��ϵ� �̵���Դϴ�.\n�ٽ� Ȯ�����ּ���.","Upload_Fail",JOptionPane.ERROR_MESSAGE);
							else { //����
								WriteEventLog(loginID+" �̵�� \""+title_input.getText()+"\" ���");
								Novel_Comic_List.add(new Novel_Comic(loginID,title_input.getText(),thumbnail,type_input.getSelectedItem().toString(),category_input.getSelectedItem().toString(),
										donation_input.getSelectedItem().toString(),0,0,-1));
								mediaCount+=1;
	
								//�̵�� ���� �Լ�							
								updateMedia(thumbnail, type_input.getSelectedItem().toString(),category_input.getSelectedItem().toString(),donation_input.getSelectedItem().toString(), "Novel_Comic",
										title_input.getText(), loginID, mediaCount-1,0,0,-1);
	
								JOptionPane.showMessageDialog(null,"�̵�� ��Ͽ� �����ϼ̽��ϴ�.\n���ϵ帳�ϴ�!","Sign_up_Success",JOptionPane.INFORMATION_MESSAGE);
								WriteNovel_ComicInfo();
								
								Vector<String> tmp = new Vector<String>();
								tmp.add(".");
								Bookmark_List.add(new Bookmark(thumbnail, tmp));
								WriteBookmark();
								
								dispose();
							}
						}
					}
				}
			});
			���.setBounds(364, 342, 108, 38);
			contentPane4.add(���);
		}
	}
	
	public class manager_screen extends JFrame { //ȸ�� ���� ȭ��
		public manager_screen() {
			setBounds(800, 250, 450, 300);
			setResizable(false);
			contentPane5 = new JPanel();
			contentPane5.setBackground(Color.WHITE);
			contentPane5.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane5.setLayout(null);
			setContentPane(contentPane5);
			
			manager_input = new JTextField();
			manager_input.setFont(new Font("���� ���", Font.PLAIN, 15));
			manager_input.setBounds(80, 147, 266, 29);
			contentPane5.add(manager_input);
			manager_input.setColumns(10);
			
			JButton ��� = new JButton("\uCDE8\uC18C");
			���.setFont(new Font("���� ���", Font.PLAIN, 15));
			���.setBackground(new Color(135, 206, 235));
			���.setBounds(80, 197, 97, 29);
			���.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			contentPane5.add(���);
			
			JButton Ȯ�� = new JButton("\uD655\uC778");
			Ȯ��.setFont(new Font("���� ���", Font.PLAIN, 15));
			Ȯ��.setBackground(new Color(135, 206, 235));
			Ȯ��.setBounds(249, 197, 97, 29);
			contentPane5.add(Ȯ��);
			Ȯ��.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (NotExistUserID(manager_input.getText()))
						JOptionPane.showMessageDialog(null, "�������� �ʴ� ������Դϴ�.\n�����ID�� �ٽ� Ȯ�����ּ���.", "Manage_Fail",JOptionPane.ERROR_MESSAGE);
					else if (CheckPosition(manager_input.getText())) {
						JOptionPane.showMessageDialog(null, "������ ID�� ������ �� �����ϴ�.\nID�� �ٽ� Ȯ�����ּ���.", "Manage_Fail",JOptionPane.ERROR_MESSAGE);
					}
					else {
						int result = JOptionPane.showConfirmDialog(null, "���� �ش� ����ڸ� �����Ͻðڽ��ϱ�?","manage",JOptionPane.YES_NO_OPTION);
						if (result==JOptionPane.YES_OPTION) {
							WriteEventLog("������, "+loginID+"�� �����, "+manager_input.getText()+" ȸ�� ����");
							
							DelUserInfo(manager_input.getText());
							
							for (int i=0;i<60;i++)
								panel_�̵��[i].removeAll();
							panel_03.repaint();
							for (int i=0; i<Video_Music_List.size(); i++)
								updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
										Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],i,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
							for (int i=0; i<Novel_Comic_List.size();i++)
								updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
										"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],i+Video_Music_List.size(),Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
							
							JOptionPane.showMessageDialog(null, "ȸ�������� �Ϸ�Ǿ����ϴ�.","Manage_Success",JOptionPane.INFORMATION_MESSAGE);
						}
						else if (result==JOptionPane.NO_OPTION) {
							JOptionPane.showMessageDialog(null, "ȸ�������� ��ҵǾ����ϴ�!","Manage_Cancel",JOptionPane.INFORMATION_MESSAGE);
						}
						dispose();
					}
				}
			});
			
			JLabel �Է� = new JLabel("�����ϰ��� �ϴ� ������� ID�� �Է����ּ���!");
			�Է�.setFont(new Font("���� ���", Font.PLAIN, 15));
			�Է�.setBounds(65, 122, 295, 15);
			contentPane5.add(�Է�);
			
			ImageIcon logo = new ImageIcon("src\\images\\manager_logo.PNG"); //�ΰ�
			Image logo_img = logo.getImage();
			Image logo_re = logo_img.getScaledInstance(280, 80, Image.SCALE_SMOOTH);
			JLabel logo_L = new JLabel(new ImageIcon(logo_re));
			logo_L.setBounds(80,16,280,80);
			contentPane5.add(logo_L);
		}
	}
	
	public class media_Info_screen extends JFrame implements ActionListener, Runnable{ //�̵�� �� ���� ȭ��
		//����
		private JProgressBar p; 
		JLabel �ð�_progress;
		String input_time;
		int hour=0;
		int min=0;
		int val_progress=0;
		Thread t;
		JButton [] player_B = new JButton[3];
		int ���_num = 0;
		
		public media_Info_screen(String thumbnail, String type, String category, String donation, String time, String title, String id, int location, int donation_n, int good_n, int etc_n) {
				donation_num = donation_n;
				good_num = good_n;
				etc_num = etc_n;
				
				input_time = time;
				
				int media_index = GetMediaIndex(thumbnail, type, category, donation, time, title, id);
				
				setResizable(false);
				setBounds(500, 150, 1000, 700);
				contentPane6 = new JPanel();
				contentPane6.setBackground(new Color(255, 255, 255));
				contentPane6.setBorder(new EmptyBorder(5, 5, 5, 5));
				contentPane6.setLayout(null);
				setContentPane(contentPane6);
				
				I_panel_02.removeAll();
				I_panel_02.repaint();
				I_panel_03.removeAll();
				I_panel_03.repaint();
				
				JPanel I_panel_01 = new JPanel();
				I_panel_01.setBackground(new Color(255, 255, 255));
				I_panel_01.setBounds(0, 0, 984, 90);
				contentPane6.add(I_panel_01);
				
				ImageIcon logo = new ImageIcon("src\\images\\logo.PNG"); //�ΰ�
				Image logo_img = logo.getImage();
				Image logo_re = logo_img.getScaledInstance(355, 60, Image.SCALE_SMOOTH);
				JLabel logo_L = new JLabel(new ImageIcon(logo_re));
				logo_L.setBounds(20,50,375, 65);
				I_panel_01.add(logo_L);
				
				I_panel_02.setBackground(new Color(135, 206, 235));
				I_panel_02.setBounds(0, 89, 728, 572);
				I_panel_02.setLayout(null);
				contentPane6.add(I_panel_02);
				
				ImageIcon thumbnail_I = new ImageIcon(thumbnail); //�����
				Image thumbnail_img = thumbnail_I.getImage();
				Image thumbnail_re = thumbnail_img.getScaledInstance(680,395, Image.SCALE_SMOOTH);
				JLabel thumbnail_L = new JLabel(new ImageIcon(thumbnail_re));
				thumbnail_L.setBounds(25,33,680, 395);
				I_panel_02.add(thumbnail_L);
				
				ImageIcon [] func = new ImageIcon[2];
				
				if (time.equals("Novel_Comic")) {
					func[0] = new ImageIcon("src\\images\\unbookmark.png"); //��å����
					func[1] = new ImageIcon("src\\images\\bookmark.png"); //å����
				}
				else {
					func[0] = new ImageIcon("src\\images\\volume.png"); //�Ҹ�
					func[1] = new ImageIcon("src\\images\\mute.png"); //���Ұ�
				}
				for (int i=0;i<2;i++) {
					Image func_img = func[i].getImage();
					Image func_re = func_img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
					func_B[i] = new JButton(new ImageIcon(func_re));
					func_B[i].setBorderPainted(false);
					func_B[i].setContentAreaFilled(false);
					func_B[i].setFocusPainted(false);
					func_B[i].setBounds(675,525,25,25);
					I_panel_02.add(func_B[i]);
				}
				if (time.equals("Novel_Comic")) {
					if (checkBookmark(thumbnail, loginID)) {
						func_B[0].setEnabled(false);
						func_B[0].setVisible(false);		
					}
					else {
						func_B[1].setEnabled(false);
						func_B[1].setVisible(false);
					}
				}
				else {
					func_B[1].setEnabled(false);
					func_B[1].setVisible(false);
				}
				
				func_B[0].addActionListener(new ActionListener() { //��å���� //�Ҹ�
					public void actionPerformed(ActionEvent e) {
						if (time.equals("Novel_Comic")) {
							AddBookmarkUser(thumbnail, loginID);
							panel_�̵��[location].removeAll();
							updateMedia(thumbnail, type, category, donation, time, title, id, location,Novel_Comic_List.get(media_index).getMediaNumInfo()[0], 
								Novel_Comic_List.get(media_index).getMediaNumInfo()[1], Novel_Comic_List.get(media_index).getMediaNumInfo()[2]);
						}
						func_B[0].setEnabled(false);
						func_B[0].setVisible(false);
						func_B[1].setEnabled(true);
						func_B[1].setVisible(true);
						I_panel_02.repaint();
					}
				});
				
				func_B[1].addActionListener(new ActionListener() { //å���� //���Ұ� //Ȯ��
					public void actionPerformed(ActionEvent e) {
						if (time.equals("Novel_Comic")) {
							DelBookmarkUser(thumbnail, loginID);
							panel_�̵��[location].removeAll();
							updateMedia(thumbnail, type, category, donation, time, title, id, location,Novel_Comic_List.get(media_index).getMediaNumInfo()[0], 
								Novel_Comic_List.get(media_index).getMediaNumInfo()[1], Novel_Comic_List.get(media_index).getMediaNumInfo()[2]);
						}
						func_B[0].setEnabled(true);
						func_B[0].setVisible(true);
						func_B[1].setEnabled(false);
						func_B[1].setVisible(false);
						I_panel_02.repaint();
					}
				});
				
				if (!time.equals("Novel_Comic")){
					String [] time_temp = new String [2];
					time_temp = time.split(":");
					int hour=Integer.parseInt(time_temp[0]);
					int min=Integer.parseInt(time_temp[1]);
					
					p = new JProgressBar();
					p.setMinimum(0);
					p.setMaximum(hour*60+min);
					p.setValue(0);
					p.setStringPainted(true);
					p.setBounds(80, 447, 485, 25);
					p.setVisible(true);
					I_panel_02.add(p);
				}
				
				if (!time.equals("Novel_Comic")) {
					ImageIcon [] player = new ImageIcon[3];
					
					player[0] = new ImageIcon("src\\images\\media_play.png");
					player[1] = new ImageIcon("src\\images\\media_stop.png");
					player[2] = new ImageIcon("src\\images\\media_zero.png");

					for (int i=0;i<3;i++) {
						Image player_img = player[i].getImage();
						Image player_re = player_img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
						player_B[i] = new JButton(new ImageIcon(player_re));
						player_B[i].setBorderPainted(false);
						player_B[i].setContentAreaFilled(false);
						player_B[i].setFocusPainted(false);
						player_B[i].setBounds(30,440,40,40);
						I_panel_02.add(player_B[i]);
					}
					player_B[1].setEnabled(false);
					player_B[1].setVisible(false);
					player_B[2].setEnabled(false);
					player_B[2].setVisible(false);
					
					player_B[0].addActionListener(new ActionListener() { //play
						public void actionPerformed(ActionEvent e) {
							WriteEventLog(loginID+" �̵�� \""+title+"\" ���");
							
							player_B[0].setEnabled(false);
							player_B[0].setVisible(false);
							player_B[1].setEnabled(true);
							player_B[1].setVisible(true);
							I_panel_02.repaint();
							
							Video_Music_List.get(media_index).addPlay();
							WriteVideo_MusicInfo();
							panel_�̵��[location].removeAll();
							updateMedia(thumbnail, type, category, donation, time, title, id, location, Video_Music_List.get(media_index).getMediaNumInfo()[0],
									Video_Music_List.get(media_index).getMediaNumInfo()[1], Video_Music_List.get(media_index).getMediaNumInfo()[2]);
							panel_03.repaint();

							etc_num+=1;
							���.setText(Integer.toString(etc_num));
							I_panel_03.repaint();
						}
					});
					
					player_B[1].addActionListener(new ActionListener() { //stop
						public void actionPerformed(ActionEvent e) {
							player_B[0].setEnabled(true);
							player_B[0].setVisible(true);
							player_B[1].setEnabled(false);
							player_B[1].setVisible(false);
							
							String [] time_temp = new String [2];
							time_temp = input_time.split(":");
							int hour_end=Integer.parseInt(time_temp[0]);
							int min_end=Integer.parseInt(time_temp[1]);
							
							if (hour==hour_end && min ==min_end+1) {
								player_B[0].setEnabled(false);
								player_B[0].setVisible(false);
								player_B[1].setEnabled(false);
								player_B[1].setVisible(false);
								player_B[2].setEnabled(true);
								player_B[2].setVisible(true);
							}
						}
					});
					
					player_B[2].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							player_B[0].setEnabled(true);
							player_B[0].setVisible(true);
							player_B[2].setEnabled(false);
							player_B[2].setVisible(false);
							hour=0;
							min=0;
							p.setValue(0);
							�ð�_progress.setText("00:00/"+input_time);
						}
					});
				}

				I_panel_03.setBackground(new Color(135, 206, 235));
				I_panel_03.setBounds(734, 89, 250, 219);
				I_panel_03.setLayout(null);
				contentPane6.add(I_panel_03);
				
				JLabel ���� = new JLabel(title);
				����.setFont(new Font("���� ���", Font.BOLD, 20));
				����.setBounds(12, 10, 226, 20);
				I_panel_03.add(����);
				
				JLabel �ۼ��� = new JLabel(id);
				�ۼ���.setFont(new Font("���� ���", Font.BOLD, 17));
				�ۼ���.setBounds(12, 37, 185, 20);
				I_panel_03.add(�ۼ���);
				
				��� = new JLabel(Integer.toString(donation_num));
				���.setFont(new Font("���� ���", Font.PLAIN, 15));
				���.setBounds(50, 110, 81, 20);
				I_panel_03.add(���);
				
				��õ = new JLabel(Integer.toString(good_num));
				��õ.setFont(new Font("���� ���", Font.PLAIN, 15));
				��õ.setBounds(50, 145, 81, 20);
				I_panel_03.add(��õ);
				
				��� = new JLabel(Integer.toString(etc_num));
				���.setFont(new Font("���� ���", Font.PLAIN, 15));
				���.setBounds(50, 180, 81, 20);
				I_panel_03.add(���);
				
				if (!time.equals("Novel_Comic")) {
					�ð�_progress = new JLabel("00:00/"+time);
					�ð�_progress.setFont(new Font("���� ���", Font.PLAIN, 20));
					�ð�_progress.setBounds(580, 450, 150, 20);
					I_panel_02.add(�ð�_progress);
				}
				
				ImageIcon[] icon_�̵�� = new ImageIcon[5];
				JLabel [] icon_�̵��_L = new JLabel[3];
				JButton [] icon_�̵��_B = new JButton[2];
				
				String image_�̵�� [] = GetImageInfo(type,category, donation);
				
				for (int i=0;i<2;i++)
					icon_�̵��[i]=new ImageIcon(image_�̵��[i]); //����, ī�װ�
				icon_�̵��[2]=new ImageIcon("src\\images\\play.png");
				icon_�̵��[3]=new ImageIcon(image_�̵��[2]); //���

				icon_�̵��[4]=new ImageIcon("src\\images\\good.png"); //��õ
				
				for (int  i=0; i<3;i++) {
					Image icon_�̵��_img = icon_�̵��[i].getImage();
					Image icon_�̵��_re = icon_�̵��_img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
					icon_�̵��_L[i]=new JLabel(new ImageIcon(icon_�̵��_re));
					I_panel_03.add(icon_�̵��_L[i]);
				}
				icon_�̵��_L[0].setBounds(13,70,25,25);
				icon_�̵��_L[1].setBounds(48,70,25,25);
				icon_�̵��_L[2].setBounds(13,175,25,25);
				
				for (int i=0;i<2;i++) {
					Image icon_�̵��_img = icon_�̵��[i+3].getImage();
					Image icon_�̵��_re = icon_�̵��_img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
					icon_�̵��_B[i]=new JButton(new ImageIcon(icon_�̵��_re));
					icon_�̵��_B[i].setBorderPainted(false);
					icon_�̵��_B[i].setContentAreaFilled(false);
					icon_�̵��_B[i].setFocusPainted(false);
					I_panel_03.add(icon_�̵��_B[i]);	
				}
				icon_�̵��_B[0].setBounds(13,105,25,25);
				icon_�̵��_B[1].setBounds(13,140,25,25);
				
				if(donation.equals("����")) {
					icon_�̵��_B[0].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (time.equals("Novel_Comic")) {
								Novel_Comic_List.get(media_index).addDonation();
								WriteNovel_ComicInfo();
								panel_�̵��[location].removeAll();
								updateMedia(thumbnail, type, category, donation, time, title, id, location,Novel_Comic_List.get(media_index).getMediaNumInfo()[0], 
									Novel_Comic_List.get(media_index).getMediaNumInfo()[1], Novel_Comic_List.get(media_index).getMediaNumInfo()[2]);
							}
							else {
								Video_Music_List.get(media_index).addDonation();
								WriteVideo_MusicInfo();
								panel_�̵��[location].removeAll();
								updateMedia(thumbnail, type, category, donation, time, title, id, location, Video_Music_List.get(media_index).getMediaNumInfo()[0],
										Video_Music_List.get(media_index).getMediaNumInfo()[1], Video_Music_List.get(media_index).getMediaNumInfo()[2]);
							}
							WriteEventLog(loginID+" �̵�� \""+title+"\" ���");
							donation_num+=100;
							���.setText(Integer.toString(donation_num));
							I_panel_03.repaint();
							panel_03.repaint();
						}
					});
				}
				else {
					���.setEnabled(false);
					���.setVisible(false);
				}
				
				icon_�̵��_B[1].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (time.equals("Novel_Comic")) {
							Novel_Comic_List.get(media_index).addGood();
							WriteNovel_ComicInfo();
							panel_�̵��[location].removeAll();
							updateMedia(thumbnail, type, category, donation, time, title, id, location,Novel_Comic_List.get(media_index).getMediaNumInfo()[0], 
								Novel_Comic_List.get(media_index).getMediaNumInfo()[1], Novel_Comic_List.get(media_index).getMediaNumInfo()[2]);
						}
						else {
							Video_Music_List.get(media_index).addGood();
							WriteVideo_MusicInfo();
							panel_�̵��[location].removeAll();
							updateMedia(thumbnail, type, category, donation, time, title, id, location, Video_Music_List.get(media_index).getMediaNumInfo()[0],
									Video_Music_List.get(media_index).getMediaNumInfo()[1], Video_Music_List.get(media_index).getMediaNumInfo()[2]);
						}
						WriteEventLog(loginID+" �̵�� \""+title+"\" ��õ");
						good_num+=1;
						��õ.setText(Integer.toString(good_num));
						I_panel_03.repaint();
						panel_03.repaint();
					}
				});
				
				if (time.equals("Novel_Comic")) {
					icon_�̵��_L[2].setEnabled(false);
					icon_�̵��_L[2].setVisible(false);
					���.setEnabled(false);
					���.setVisible(false);
				}
				
				JPanel I_panel_04 = new JPanel();
				I_panel_04.setBackground(new Color(135, 206, 235));
				I_panel_04.setBounds(734, 314, 250, 347);
				I_panel_04.setLayout(null);
				contentPane6.add(I_panel_04);
				
				JLabel ��� = new JLabel("\uB313\uAE00");
				���.setBackground(new Color(255, 255, 255));
				���.setHorizontalAlignment(SwingConstants.CENTER);
				���.setFont(new Font("���� ���", Font.BOLD, 25));
				���.setBounds(0, 0, 250, 30);
				I_panel_04.add(���);
				
				JPanel I_panel_04_01 = new JPanel();
				I_panel_04_01.setBackground(SystemColor.control);
				I_panel_04_01.setLayout(new GridLayout(30,1,3,3));
				I_panel_04.add(I_panel_04_01);
				
				JPanel [] I_panel_��� = new JPanel [30];
				for (int i=0;i<30;i++) {
					I_panel_���[i] = new JPanel();
					I_panel_���[i].setLayout(null);
					I_panel_���[i].setBackground(Color.white);
					I_panel_���[i].setPreferredSize(new Dimension(100,60));
					I_panel_���[i].setMaximumSize(getPreferredSize());
					I_panel_���[i].setMinimumSize(getPreferredSize());
					I_panel_04_01.add(I_panel_���[i]);
				}
				JScrollPane scroll = new JScrollPane(I_panel_04_01);
				scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scroll.setBounds(7, 35, 238, 240);
				I_panel_04.add(scroll);
				
				JTextField input_��� = new JTextField();
				input_���.setFont(new Font("���� ���", Font.PLAIN, 13));
				input_���.setBounds(7, 285, 169, 54);
				input_���.setColumns(10);
				I_panel_04.add(input_���);

				JButton ���_B = new JButton("\uB313\uAE00");
				���_B.setBackground(SystemColor.control);
				���_B.setFont(new Font("���� ���", Font.PLAIN, 12));
				���_B.setBounds(181, 285, 63, 54);
				���_B.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (input_���.getText().isEmpty())
							JOptionPane.showMessageDialog(null, "��� ������ �Է��� �� ����� �������ּ���.","Comment_Fail",JOptionPane.ERROR_MESSAGE);
						else {
							if (���_num<30) {
								WriteEventLog(loginID+" �̵�� \""+title+"\" ��� �ۼ�");
								JLabel ���_�ۼ��� = new JLabel("�ۼ��� : "+loginID);
								JLabel ���_���� = new JLabel(input_���.getText());
								���_�ۼ���.setFont(new Font("���� ���", Font.PLAIN, 15));
								���_����.setFont(new Font("���� ���", Font.PLAIN, 15));
								���_�ۼ���.setBounds(5,5, 100, 14);
								���_����.setBounds(5,30, 220, 14);
								I_panel_���[���_num].add(���_�ۼ���);
								I_panel_���[���_num].add(���_����);
								I_panel_04.repaint();
								���_num++;
							}
							else {
								JOptionPane.showMessageDialog(null, "����� �ۼ��� ������ �����ϴ�.\n�˼��մϴ�.","Comment_Fail",JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				});
				I_panel_04.add(���_B);
				
				if (!time.equals("Novel_Comic")) {
					player_B[0].addActionListener(this);
					player_B[1].addActionListener(this);
				}
			}
		
		public void run() {
			String [] time_temp = new String [2];
			time_temp = input_time.split(":");
			int hour_end=Integer.parseInt(time_temp[0]);
			int min_end=Integer.parseInt(time_temp[1]);
			String hour_s;
			String min_s;
			try {
				while(hour<hour_end || min<=min_end) {
					if (hour<10)
						hour_s = "0".concat(Integer.toString(hour));
					else
						hour_s = Integer.toString(hour);
					if (min<10)
						min_s = "0".concat(Integer.toString(min));
					else
						min_s = Integer.toString(min);
					�ð�_progress.setText(hour_s+":"+min_s+"/"+input_time);
					I_panel_02.repaint();
					Thread.sleep(10); //0.01��
					min++;
					if (min==60) {
						hour++;
						min=0;
					}
					p.setValue(hour*60+min);
				}
			}
			catch(Exception ex) {}
		}
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==player_B[0]) {
				t=new Thread(this);
				t.start();
			}
			if (e.getSource()==player_B[1]) {
				t.interrupt();
			}
		}
	}
}