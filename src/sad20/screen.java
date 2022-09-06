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
	protected int donation_num; //기부금
	protected int good_num; //추천수
	
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
	private int play_num; //무조건 양수일 것
	
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
	private int bookmark_num; //필요없는 변수
	
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

	//전역 변수
	private JPanel contentPane; //main_screen
	private JTextField 검색창;
	private JButton 로그인;
	private JButton 로그아웃;
	private JButton 회원가입;
	private JButton 회원탈퇴;
	private JButton 회원관리;
	private JLabel 환영;
	
	private JPanel panel_03 = new JPanel();
	private JPanel [] panel_미디어 = new JPanel[60]; //미디어 Panel
	
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
	private JLabel 기부;
	private JLabel 추천;
	private JLabel 재생;
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
	private String Selected_Type = "전체";
	private String Selected_Category = "전체";
	
	private String 유형_btn_name[] = {"전체", "동영상", "음악", "소설", "만화"};
	private String 카테고리_btn_name[] = {"전체", "게임", "스포츠", "학습", "음식"};
	private JButton [] 유형_btn = new JButton[유형_btn_name.length];
	private JButton [] 카테고리_btn = new JButton[카테고리_btn_name.length];

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
		for (int i=0;i<유형_btn_name.length;i++) {
			유형_btn[i].setForeground(new Color(0, 0, 0));
			유형_btn[i].setBackground(Color.white);
		}
		for (int i=0;i<카테고리_btn_name.length;i++) {
			카테고리_btn[i].setForeground(new Color(0, 0, 0));
			카테고리_btn[i].setBackground(Color.white);	
		}
		유형_btn[0].setForeground(new Color(255, 0, 0));
		유형_btn[0].setBackground(SystemColor.controlHighlight);
		카테고리_btn[0].setForeground(new Color(255, 0, 0));
		카테고리_btn[0].setBackground(SystemColor.controlHighlight);
		Selected_Type = "전체";
		Selected_Category = "전체";
	}
	
	public String[] GetImageInfo(String t, String c, String d) {
		String tI=null, cI=null, dI=null;
		switch (t) { //유형
		case "동영상": tI = "src\\images\\video.png";
			break;
		case "음악": tI = "src\\images\\music.png";
			break;
		case "만화": tI = "src\\images\\comic.png";
			break;
		case "소설": tI = "src\\images\\novel.png";
			break;
		}
		switch (c) { //카테고리
		case "게임": cI = "src\\images\\game.png";
			break;
		case "스포츠": cI = "src\\images\\sport.png";
			break;
		case "학습": cI = "src\\images\\study.png";
			break;
		case "음식": cI = "src\\images\\food.png";
			break;
		}
		switch (d) { //기부
		case "가능": dI = "src\\images\\money.png";
			break;
		case "불가능": dI = "src\\images\\ban.png";
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
	
	public boolean NotExistTitle(String inputTitle) { //없는 title이면 true
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
	
	public boolean NotExistMyMedia(String loginID) { //myMedia 없으면 true
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
	
	public boolean NotExistUserID(String inputID) { //없는 id면 true
		for (int i=0;i<UserList.size();i++) {
			if (UserList.get(i).getUserID().equals(inputID)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean CheckPosition(String DelID) { //관리자면 true
		for (int i=0;i<UserList.size();i++) {
			if (UserList.get(i).getUserID().equals(DelID)&&UserList.get(i).getUserPosition().equals("관리자"))
				return true;
		}
		return false;
	}
	
	public boolean CheckUserPW(String inputID,String inputPW) { //모두 일치하면 true
		for (int i=0;i<UserList.size();i++) {
			if (UserList.get(i).getUserID().equals(inputID)&&UserList.get(i).getUserPW().equals(inputPW))
				return true;
		}
		return false;
	}
	
	public boolean CheckTumbnail(String Input_Thumbnail) { //일치하면 true
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
			System.out.println("파일 "+in.getName()+"을 찾을 수 없습니다.");
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
			System.out.println("IOException 오류가 발생했습니다.");
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
			System.out.println("파일 "+in.getName()+"을 찾을 수 없습니다.");
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
			System.out.println("IOException 오류가 발생했습니다.");
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
			System.out.println("파일 "+in.getName()+"을 찾을 수 없습니다.");
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
			System.out.println("IOException 오류가 발생했습니다.");
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
			System.out.println("파일 "+in.getName()+"을 찾을 수 없습니다.");
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
			System.out.println("IOException 오류가 발생했습니다.");
			System.exit(0);
		}
	}

	void DelBookmarkInfo(String thumbnail) { //파일 삭제시
		for (int i=0; i<Bookmark_List.size();i++) {
			if (Bookmark_List.get(i).getBook().equals(thumbnail))
				Bookmark_List.remove(i);
		}
		WriteBookmark();
	}
	
	void DelBookmarkUser(String thumbnail, String id) { //유저가 북마크 삭제시
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
	
	void AddBookmarkUser(String thumbnail, String id) { //유저가 북마크 추가시
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
			System.out.println("IOException 오류가 발생했습니다.");
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
			System.out.println("파일 "+in.getName()+"을 찾을 수 없습니다.");
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
			System.out.println("IOException 오류가 발생했습니다.");
			System.exit(0);
		}
	}
	
	String CopyThumbnail(String path, String format) { //이미지 복사 및 저장
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
	
	void updateMedia(String thumbnail, String type, String category, String donation, String time, String title, String id, int location, int donation_num, int good_num, int etc_num) { //time = Novel_Comic 이면 Novel_Comic
		if (location<60) { //대쉬보드 최대 60개
			ImageIcon thumbnail_미디어 = new ImageIcon(thumbnail); //썸네일
			Image thumbnail_미디어_img = thumbnail_미디어.getImage();
			Image thumbnail_미디어_re = thumbnail_미디어_img.getScaledInstance(220, 115, Image.SCALE_SMOOTH);
			JButton thumbnail_미디어_B = new JButton(new ImageIcon(thumbnail_미디어_re));
			thumbnail_미디어_B.setBorderPainted(false);
			thumbnail_미디어_B.setContentAreaFilled(false);
			thumbnail_미디어_B.setFocusPainted(false);
			thumbnail_미디어_B.setBounds(10,10,220,115);
			thumbnail_미디어_B.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (loginID==null)
						JOptionPane.showMessageDialog(null, "로그인 후 이용하실 수 있습니다.","Media_Info_Fail",JOptionPane.ERROR_MESSAGE);
					else {
						WriteEventLog(loginID+" 미디어 \""+title+"\" 클릭");
						media_Info_screen media_Info_frame = new media_Info_screen(thumbnail, type, category, donation, time, title, id, location, donation_num, good_num, etc_num);
						media_Info_frame.setVisible(true);
					}
				}
			});
			panel_미디어[location].add(thumbnail_미디어_B);
			
			ImageIcon [] icon_미디어 = new ImageIcon[3];
			JLabel [] icon_미디어_L = new JLabel[3];
		
			String [] icon_이미지= GetImageInfo(type,category,donation);
			
			for (int i=0; i<3;i++) {
				icon_미디어[i]=new ImageIcon(icon_이미지[i]);			
				Image icon_미디어_img = icon_미디어[i].getImage();
				Image icon_미디어_re = icon_미디어_img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
				icon_미디어_L[i]=new JLabel(new ImageIcon(icon_미디어_re));
				icon_미디어_L[i].setBounds(10+35*i,130,25,25);
				panel_미디어[location].add(icon_미디어_L[i]);
			}
			if (time.equals("Novel_Comic")) { //Novel_Comic
				JLabel [] text_미디어 = new JLabel[2];
				text_미디어[0] = new JLabel(title);
				text_미디어[1] = new JLabel(id);
				for (int i=0;i<2;i++) {
					text_미디어[i].setFont(new Font("한컴 고딕", Font.PLAIN, 15));
				}
				text_미디어[0].setBounds(10,165,230,30);
				text_미디어[1].setBounds(10,190,230,30);
				for (int i=0;i<2;i++)
					panel_미디어[location].add(text_미디어[i]);
		
				if (checkBookmark(thumbnail, loginID)) {
					ImageIcon bookmark_미디어 = new ImageIcon("src\\images\\bookmark.PNG"); //북마크
					Image bookmark_미디어_img = bookmark_미디어.getImage();
					Image bookmark_미디어_re = bookmark_미디어_img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
					JLabel bookmark_미디어_L = new JLabel(new ImageIcon(bookmark_미디어_re));
					bookmark_미디어_L.setBounds(205,130,25,25);
					panel_미디어[location].add(bookmark_미디어_L);
				}
			}
			else { //Video_Music
				JLabel [] text_미디어 = new JLabel[3]; 
				text_미디어[0] = new JLabel(time);
				text_미디어[1] = new JLabel(title);
				text_미디어[2] = new JLabel(id);
				for (int i=0;i<3;i++)
					text_미디어[i].setFont(new Font("한컴 고딕", Font.PLAIN, 15));
				text_미디어[0].setBounds(180,130,100,30);
				text_미디어[1].setBounds(10,165,230,30);
				text_미디어[2].setBounds(10,190,230,30);
				for (int i=0;i<3;i++)
					panel_미디어[location].add(text_미디어[i]);	
			}
			
			ImageIcon del_미디어 = new ImageIcon("src\\images\\delete.PNG"); //삭제
			Image del_미디어_img = del_미디어.getImage();
			Image del_미디어_re = del_미디어_img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
			JButton del_미디어_B = new JButton(new ImageIcon(del_미디어_re));
			del_미디어_B.setBorderPainted(false);
			del_미디어_B.setContentAreaFilled(false);
			del_미디어_B.setFocusPainted(false);
			del_미디어_B.setBounds(200,190,25,25);
			del_미디어_B.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int result = JOptionPane.showConfirmDialog(null, "정말 해당 미디어를 삭제하시겠습니까?","delete",JOptionPane.YES_NO_OPTION);
					if (result==JOptionPane.YES_OPTION) {
						WriteEventLog(loginID+" 미디어 \""+title+"\" 삭제");
						DelMediaInfo(thumbnail, type, category, donation, time, title, id);
						DelBookmarkInfo(thumbnail);
						
						for (int i=0;i<60;i++)
							panel_미디어[i].removeAll();
						panel_03.repaint();
						for (int i=0; i<Video_Music_List.size(); i++)
							updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
									Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],i,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
						for (int i=0; i<Novel_Comic_List.size();i++)
							updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
									"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],i+Video_Music_List.size(),Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
						
						JOptionPane.showMessageDialog(null, "미디어 삭제가 완료되었습니다.","Delete_Success",JOptionPane.INFORMATION_MESSAGE);
					}
					else if (result==JOptionPane.NO_OPTION) {
						JOptionPane.showMessageDialog(null, "미디어 삭제가 취소되었습니다!","Delete_Cancel",JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
			panel_미디어[location].add(del_미디어_B);

			if (loginID==null) {
				del_미디어_B.setEnabled(false);
				del_미디어_B.setVisible(false);
			}
			else {
				if (GetPosition(loginID).equals("관리자")) {
					del_미디어_B.setEnabled(true);
					del_미디어_B.setVisible(true);
				}
				else { //사용자
					if (id.equals(loginID)) {
						del_미디어_B.setEnabled(true);
						del_미디어_B.setVisible(true);	
					}
					else {
						del_미디어_B.setEnabled(false);
						del_미디어_B.setVisible(false);
					}
				}
			}
			panel_03.repaint();
		}
	}
	
	//화면 코드 시작 디자인 시작
	
	public screen() { //메인 화면
		
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
		
		환영 = new JLabel("");
		환영.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
		환영.setBounds(356, 13, 250, 15);
		panel_01.add(환영);
		환영.setEnabled(false);
		환영.setVisible(false);

		로그인 = new JButton("\uB85C\uADF8\uC778"); //로그인 버튼
		로그인.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
		로그인.setBackground(new Color(135, 206, 235));
		로그인.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login_screen login_frame = new login_screen();
				login_frame.setVisible(true);
			}
		});
		로그인.setBounds(850, 10, 113, 23);
		panel_01.add(로그인);
		
		로그아웃 = new JButton("로그아웃"); //로그아웃 버튼
		로그아웃.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
		로그아웃.setBackground(new Color(135, 206, 235));
		로그아웃.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "로그아웃에 성공하셨습니다.\n다음에 또 오세요!","Logout_Success",JOptionPane.INFORMATION_MESSAGE);
				if (loginPosition.equals("관리자")) {
					WriteEventLog("관리자, "+loginID+" 로그아웃");
					회원관리.setEnabled(false);
					회원관리.setVisible(false);
				}
				else if (loginPosition.equals("사용자")) {
					WriteEventLog("사용자, "+loginID+" 로그아웃");
					회원탈퇴.setEnabled(false);
					회원탈퇴.setVisible(false);
				}
				loginID = null;
				
				loginPosition=null;
				로그인.setEnabled(true);
				로그인.setVisible(true);
				로그아웃.setEnabled(false);
				로그아웃.setVisible(false);
				회원가입.setEnabled(true);
				회원가입.setVisible(true);
				회원탈퇴.setEnabled(false);
				회원탈퇴.setVisible(false);
				환영.setEnabled(false);
				환영.setVisible(false);
				
				//새로고침
				for (int i=0;i<60;i++)
					panel_미디어[i].removeAll();
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
		로그아웃.setBounds(850, 10, 113, 23);
		panel_01.add(로그아웃);
		로그아웃.setEnabled(false);
		로그아웃.setVisible(false);
		
		회원가입 = new JButton("\uD68C\uC6D0\uAC00\uC785"); //회원가입 버튼
		회원가입.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
		회원가입.setBackground(new Color(135, 206, 235));
		회원가입.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sign_up_screen sign_up_frame = new sign_up_screen();
				sign_up_frame.setVisible(true);
			}
		});
		회원가입.setBounds(850, 43, 113, 23);
		panel_01.add(회원가입);
		
		회원탈퇴 = new JButton("회원탈퇴"); //회원탈퇴 버튼
		회원탈퇴.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
		회원탈퇴.setBackground(new Color(135, 206, 235));
		회원탈퇴.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "정말 회원탈퇴를 하실거에요?\n너무 아쉬워요.","sign_out",JOptionPane.YES_NO_OPTION);
				if (result==JOptionPane.YES_OPTION) {
					WriteEventLog(loginID+" 회원탈퇴");
					
					DelUserInfo(loginID);
					loginID=null;
	
					로그인.setEnabled(true);
					로그인.setVisible(true);
					로그아웃.setEnabled(false);
					로그아웃.setVisible(false);
					회원가입.setEnabled(true);
					회원가입.setVisible(true);
					회원탈퇴.setEnabled(false);
					회원탈퇴.setVisible(false);
					환영.setEnabled(false);
					환영.setVisible(false);
					
					//새로고침
					for (int i=0;i<60;i++)
						panel_미디어[i].removeAll();
					panel_03.repaint();
					for (int i=0; i<Video_Music_List.size(); i++)
						updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
								Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],i,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
					for (int i=0; i<Novel_Comic_List.size();i++)
						updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
								"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],i+Video_Music_List.size(),Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
					SetButtonColor();
		
					JOptionPane.showMessageDialog(null, "회원탈퇴가 완료되었습니다.\n안녕히 가세요!","Sign_out_Success",JOptionPane.INFORMATION_MESSAGE);
				}
				else if (result==JOptionPane.NO_OPTION) {
					JOptionPane.showMessageDialog(null, "회원탈퇴가 취소되었습니다!\n잘 생각하셨어요!","Sign_out_Cancel",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		회원탈퇴.setBounds(850, 43, 113, 23);
		panel_01.add(회원탈퇴);
		회원탈퇴.setEnabled(false);
		회원탈퇴.setVisible(false);
		
		회원관리 = new JButton("회원관리"); //회원가입 버튼
		회원관리.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
		회원관리.setBackground(new Color(135, 206, 235));
		회원관리.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manager_screen manager_frame = new manager_screen();
				manager_frame.setVisible(true);
			}
		});
		회원관리.setBounds(850, 43, 113, 23);
		panel_01.add(회원관리);
		회원관리.setEnabled(false);
		회원관리.setVisible(false);
		
		JButton 미디어등록 = new JButton("\uBBF8\uB514\uC5B4 \uB4F1\uB85D"); //미디어 등록 버튼
		미디어등록.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
		미디어등록.setBackground(new Color(135, 206, 235));
		미디어등록.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (loginID==null)
					JOptionPane.showMessageDialog(null, "로그인 후 이용하실 수 있습니다.","Upload_Fail",JOptionPane.ERROR_MESSAGE);
				else {
					upload_screen upload_frame = new upload_screen();
					upload_frame.setVisible(true);
				}
			}
		});
		미디어등록.setBounds(715, 10, 113, 23);
		panel_01.add(미디어등록);
		
		검색창 = new JTextField();
		검색창.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
		검색창.setForeground(new Color(0, 0, 0));
		검색창.setBounds(355, 35, 250, 30);
		panel_01.add(검색창);
		검색창.setColumns(10);
		
		JPanel panel_02 = new JPanel();
		panel_02.setBackground(new Color(255, 255, 255));
		panel_02.setBounds(0, 75, 985, 125);
		panel_02.setLayout(new GridLayout(1,2,3,0));
		contentPane.add(panel_02);
		
		JPanel panel_02_01 = new JPanel();
		panel_02_01.setBackground(new Color(135, 206, 235));
		panel_02.add(panel_02_01);
		panel_02_01.setLayout(new GridLayout(2,1,0,0));
		
		JPanel 유형_panel = new JPanel();
		유형_panel.setBackground(new Color(135, 206, 235));
		panel_02_01.add(유형_panel);
		유형_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel 유형 = new JLabel("   \uC720\uD615");
		유형.setFont(new Font("한컴 고딕", Font.PLAIN, 30));
		유형_panel.add(유형);
		
		JPanel 유형_panel_2 = new JPanel();
		유형_panel_2.setBackground(new Color(135, 206, 235));
		panel_02_01.add(유형_panel_2);
		유형_panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		for (int i = 0;i<유형_btn_name.length;i++) {
			유형_btn[i] = new JButton(유형_btn_name[i]);
			유형_btn[i].setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			유형_btn[i].setBackground(Color.WHITE);
			유형_btn[i].setPreferredSize(new Dimension(85,40));
			유형_btn[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton b = (JButton)e.getSource();
					for (int i=0;i<유형_btn_name.length;i++) {
						유형_btn[i].setForeground(new Color(0, 0, 0));
						유형_btn[i].setBackground(Color.white);
					}
					b.setForeground(new Color(255,0,0));
					b.setBackground(SystemColor.controlHighlight);
					
					Selected_Type=b.getText();
					
					for (int i=0;i<60;i++)
						panel_미디어[i].removeAll();
					panel_03.repaint();
					switch(Selected_Type) {
					case "전체":
						if (Selected_Category.equals("전체")) { //유형 전체, 카테고리 전체
							for (int i=0; i<Video_Music_List.size(); i++)
								updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
										Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],i,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
							for (int i=0; i<Novel_Comic_List.size();i++)
								updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
										"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],i+Video_Music_List.size(),Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
						}
						else { //유형 전체, 카테고리 선택
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
					case "만화" : case "소설" : case "음악" : case "동영상" :
						if (Selected_Category.equals("전체")) { //유형 선택, 카테고리 전체
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
						else { //유형 선택, 카테고리 선택
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
			유형_panel_2.add(유형_btn[i]);
		}
		유형_btn[0].setForeground(new Color(255, 0, 0));
		유형_btn[0].setBackground(SystemColor.controlHighlight);
		
		JPanel panel_02_02 = new JPanel();
		panel_02_02.setBackground(new Color(135, 206, 235));
		panel_02.add(panel_02_02);
		panel_02_02.setLayout(new GridLayout(2,1,0,0));
		
		JPanel 카테고리_panel = new JPanel();
		카테고리_panel.setBackground(new Color(135, 206, 235));
		panel_02_02.add(카테고리_panel);
		카테고리_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel 카테고리 = new JLabel("   \uCE74\uD14C\uACE0\uB9AC");
		카테고리_panel.add(카테고리);
		카테고리.setFont(new Font("한컴 고딕", Font.PLAIN, 30));
		
		JPanel 카테고리_panel_2 = new JPanel();
		카테고리_panel_2.setBackground(new Color(135, 206, 235));
		panel_02_02.add(카테고리_panel_2);
		
		for (int i = 0;i<카테고리_btn_name.length;i++) {
			카테고리_btn[i] = new JButton(카테고리_btn_name[i]);
			카테고리_btn[i].setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			카테고리_btn[i].setBackground(Color.WHITE);
			카테고리_btn[i].setPreferredSize(new Dimension(85,40));
			카테고리_btn[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton b = (JButton)e.getSource();
					for (int i=0;i<카테고리_btn_name.length;i++) {
						카테고리_btn[i].setForeground(new Color(0, 0, 0));
						카테고리_btn[i].setBackground(Color.white);
					}
					b.setForeground(new Color(255,0,0));
					b.setBackground(SystemColor.controlHighlight);
					
					Selected_Category=b.getText();
					for (int i=0;i<60;i++)
						panel_미디어[i].removeAll();
					panel_03.repaint();
					switch(Selected_Type) {
					case "전체":
						if (Selected_Category.equals("전체")) { //유형 전체, 카테고리 전체
							for (int i=0; i<Video_Music_List.size(); i++)
								updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
										Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],i,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
							for (int i=0; i<Novel_Comic_List.size();i++)
								updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
										"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],i+Video_Music_List.size(),Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
						}
						else { //유형 전체, 카테고리 선택
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
					case "만화" : case "소설" : case "음악" : case "동영상" :
						if (Selected_Category.equals("전체")) { //유형 선택, 카테고리 전체
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
						else { //유형 선택, 카테고리 선택
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
			카테고리_panel_2.add(카테고리_btn[i]);
		}
		카테고리_btn[0].setForeground(new Color(255, 0, 0));
		카테고리_btn[0].setBackground(SystemColor.controlHighlight);
	
		ImageIcon logo = new ImageIcon("src\\images\\logo.PNG"); //로고
		JButton logo_B = new JButton(logo);
		logo_B.setBorderPainted(false);
		logo_B.setContentAreaFilled(false);
		logo_B.setFocusPainted(false);
		logo_B.setBounds(20,10,300,55);
		logo_B.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i=0;i<60;i++)
					panel_미디어[i].removeAll();
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
		
		ImageIcon search = new ImageIcon("src\\images\\glass.png"); //돋보기
		Image search_img = search.getImage();
		Image search_re = search_img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		JButton search_B = new JButton(new ImageIcon(search_re));
		search_B.setBorderPainted(false);
		search_B.setContentAreaFilled(false);
		search_B.setFocusPainted(false);
		search_B.setBounds(618,35,25,25);
		search_B.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (검색창.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "검색어를 입력해주세요.","Search_Fail",JOptionPane.ERROR_MESSAGE);
				else if (NotExistTitle(검색창.getText())) //해당 검색어와 일치하는 미디어가 없을 경우
					JOptionPane.showMessageDialog(null, "검색어 ["+검색창.getText()+"] 에 해당하는 검색 결과가 없습니다.\n다른 검색어를 입력해주세요.","Search_Fail",JOptionPane.ERROR_MESSAGE);
				else {				
					int temp = 0;
					for (int i=0;i<60;i++)
						panel_미디어[i].removeAll();
					panel_03.repaint();
					for (int i=0; i<Video_Music_List.size(); i++) {
						if (Video_Music_List.get(i).getMediaAllInfo()[1].contains(검색창.getText())) {
							updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
									Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],temp,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
							temp++;
						}
					}
					for (int i=0; i<Novel_Comic_List.size();i++) {
						if (Novel_Comic_List.get(i).getMediaAllInfo()[1].contains(검색창.getText())) {
							updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
									"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],temp,Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
							temp++;
						}
					}
					SetButtonColor();
					JOptionPane.showMessageDialog(null, "검색어 ["+검색창.getText()+"] 검색 결과입니다.","Search_Success",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		panel_01.add(search_B);
		
		JButton 내미디어 = new JButton("내 미디어"); //내 미디어 버튼
		내미디어.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
		내미디어.setBackground(new Color(135, 206, 235));
		내미디어.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (loginID==null)
					JOptionPane.showMessageDialog(null, "로그인 후 이용하실 수 있습니다.","My_media_Fail",JOptionPane.ERROR_MESSAGE);
				else if (NotExistMyMedia(loginID))
					JOptionPane.showMessageDialog(null, "등록된 내 미디어가 없습니다.\n새로운 미디어를 등록해주세요.","My_media_Fail",JOptionPane.ERROR_MESSAGE);
				else {
					int temp = 0;
					for (int i=0;i<60;i++)
						panel_미디어[i].removeAll();
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
					JOptionPane.showMessageDialog(null, "내 미디어 검색 결과입니다.","My_media_Success",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		내미디어.setBounds(715, 43, 113, 23);
		panel_01.add(내미디어);
		
		//미디어 Panel
		for (int i =0;i<60;i++) {
			panel_미디어[i] = new JPanel();
			panel_미디어[i].setBackground(new Color(135, 206, 235));
			panel_미디어[i].setPreferredSize(new Dimension(200,230));
			panel_03.add(panel_미디어[i]);
			panel_미디어[i].setLayout(null);
			panel_미디어[i].setMaximumSize(getPreferredSize());
			panel_미디어[i].setMinimumSize(getPreferredSize());
		}

		panel_03.setBackground(new Color(255, 255, 255));
		panel_03.setBounds(0, 203, 985, 460);
		panel_03.setLayout(new GridLayout(15,4,3,3));

		//등록된 미디어 갱신
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

	private class sign_up_screen extends JFrame{ //회원가입 화면
		public sign_up_screen() {
			setBounds(800, 200, 350, 550);
			setResizable(false);
			contentPane2 = new JPanel();
			contentPane2.setBackground(new Color(255, 255, 255));
			contentPane2.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane2.setLayout(null);
			setContentPane(contentPane2);
			
			name_input = new JTextField();
			name_input.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			name_input.setBounds(34, 127, 262, 29);
			contentPane2.add(name_input);
			name_input.setColumns(10);
			
			JLabel name = new JLabel("\uC774\uB984");
			name.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			name.setBounds(34, 102, 57, 15);
			contentPane2.add(name);
			
			id_input = new JTextField();
			id_input.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			id_input.setColumns(10);
			id_input.setBounds(34, 205, 262, 29);
			contentPane2.add(id_input);
			
			JLabel id = new JLabel("ID");
			id.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			id.setBounds(34, 180, 57, 15);
			contentPane2.add(id);
			
			pw_input = new JPasswordField();
			pw_input.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			pw_input.setColumns(10);
			pw_input.setBounds(34, 283, 262, 29);
			contentPane2.add(pw_input);
			
			JLabel pw = new JLabel("PW");
			pw.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			pw.setBounds(34, 258, 57, 15);
			contentPane2.add(pw);
			
			JLabel position = new JLabel("\uC720\uD615");
			position.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			position.setBounds(34, 331, 57, 15);
			contentPane2.add(position);
			
			JComboBox position_input = new JComboBox();
			position_input.setBackground(new Color(255, 255, 255));
			position_input.setModel(new DefaultComboBoxModel(new String[] {"\uC0AC\uC6A9\uC790", "\uAD00\uB9AC\uC790"}));
			position_input.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			position_input.setBounds(34, 356, 262, 29);
			contentPane2.add(position_input);
			
			JButton 취소 = new JButton("\uCDE8\uC18C");
			취소.setBackground(new Color(135, 206, 235));
			취소.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			취소.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			취소.setBounds(34, 430, 108, 38);
			contentPane2.add(취소);
			
			JButton 확인 = new JButton("\uD655\uC778");
			확인.setBackground(new Color(135, 206, 235));
			확인.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			확인.setBounds(188, 430, 108, 38);
			확인.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (name_input.getText().isEmpty()||id_input.getText().isEmpty()||pw_input.getText().isEmpty())
						JOptionPane.showMessageDialog(null, "입력되지 않은 항목이 있습니다.\n다시 확인해주세요.","Sign_up_Fail",JOptionPane.ERROR_MESSAGE);
					else {
						if(id_input.getText().equals("."))
							JOptionPane.showMessageDialog(null, "등록할 수 없는 ID입니다.\n다시 입력해주세요.","Sign_up_Fail",JOptionPane.ERROR_MESSAGE);
						else if (NotExistUserID(id_input.getText())){
							if (position_input.getSelectedItem().toString().equals("관리자")) { //관리자일 경우 등록 암호 확인
								String manager_input_PW = JOptionPane.showInputDialog("관리자 등록 암호를 입력하세요.");
								if (manager_input_PW==null)
									dispose();
								else if (manager_input_PW.equals(manager_PW)) { //등록 암호 일치
									WriteEventLog("관리자, "+id_input.getText()+" 회원가입");
									UserList.add(new UserInfo(name_input.getText(),id_input.getText(),pw_input.getText(),position_input.getSelectedItem().toString()));
									JOptionPane.showMessageDialog(null, "회원가입에 성공하셨습니다.\n축하드립니다!","Sign_up_Success",JOptionPane.INFORMATION_MESSAGE);
									WriteUserInfo();
									dispose();
								}
								else //등록 암호 불일치
									JOptionPane.showMessageDialog(null, "관리자 등록 암호가 틀렸습니다.\n다시 확인해주세요.","Sign_up_Fail",JOptionPane.ERROR_MESSAGE);
							}
							else { //사용자 등록일 경우
								WriteEventLog("사용자, "+id_input.getText()+" 회원가입");
								UserList.add(new UserInfo(name_input.getText(),id_input.getText(),pw_input.getText(),position_input.getSelectedItem().toString()));
								JOptionPane.showMessageDialog(null, "회원가입에 성공하셨습니다.\n축하드립니다!","Sign_up_Success",JOptionPane.INFORMATION_MESSAGE);
								WriteUserInfo();
								dispose();
							}
						}		
						else
							JOptionPane.showMessageDialog(null, "이미 존재하는 ID입니다.\n다시 입력해주세요.","Sign_up_Fail",JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			contentPane2.add(확인);
			
			ImageIcon logo = new ImageIcon("src\\images\\sign_up_logo.PNG"); //로고
			Image logo_img = logo.getImage();
			Image logo_re = logo_img.getScaledInstance(280, 80, Image.SCALE_SMOOTH);
			JLabel logo_L = new JLabel(new ImageIcon(logo_re));
			logo_L.setBounds(30,10,280,80);
			contentPane2.add(logo_L);
		}
	}
	
	private class login_screen extends JFrame { //로그인 화면
		public login_screen() {
			setBounds(800, 200, 350, 550);
			setResizable(false);
			contentPane3 = new JPanel();
			contentPane3.setBackground(new Color(255, 255, 255));
			contentPane3.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane3.setLayout(null);
			setContentPane(contentPane3);
			
			ID_input = new JTextField();
			ID_input.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			ID_input.setColumns(10);
			ID_input.setBounds(34, 185, 262, 29);
			contentPane3.add(ID_input);
			
			JLabel ID = new JLabel("ID");
			ID.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			ID.setBounds(34, 160, 57, 15);
			contentPane3.add(ID);
			
			PW_input = new JPasswordField();
			PW_input.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			PW_input.setColumns(10);
			PW_input.setBounds(34, 282, 262, 29);
			contentPane3.add(PW_input);
			
			JLabel PW = new JLabel("PW");
			PW.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			PW.setBounds(34, 257, 57, 15);
			contentPane3.add(PW);
			
			JButton 취소 = new JButton("\uCDE8\uC18C");
			취소.setBackground(new Color(135, 206, 235));
			취소.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			취소.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			취소.setBounds(34, 430, 108, 38);
			contentPane3.add(취소);
			
			JButton 확인 = new JButton("\uD655\uC778");
			확인.setBackground(new Color(135, 206, 235));
			확인.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			확인.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (ID_input.getText().isEmpty()||PW_input.getText().isEmpty())
						JOptionPane.showMessageDialog(null, "입력되지 않은 항목이 있습니다.\n다시 확인해주세요.","Login_Fail",JOptionPane.ERROR_MESSAGE);
					else {
						if (NotExistUserID(ID_input.getText())){ //없는 아이디 입력
							JOptionPane.showMessageDialog(null, "존재하지 않는 ID입니다.\n다시 입력해주세요.","Login_Fail",JOptionPane.ERROR_MESSAGE);
						}
						else if (CheckUserPW(ID_input.getText(),PW_input.getText())) { //로그인 성공
							loginID=ID_input.getText();
							loginPosition=GetPosition(loginID);
							JOptionPane.showMessageDialog(null, "로그인에 성공하셨습니다.\n축하드립니다!","Login_Success",JOptionPane.INFORMATION_MESSAGE);
							로그인.setEnabled(false);
							로그인.setVisible(false);
							로그아웃.setEnabled(true);
							로그아웃.setVisible(true);
							회원가입.setEnabled(false);
							회원가입.setVisible(false);
							환영.setText(loginPosition+", "+loginID+"님 환영합니다!");
							환영.setEnabled(true);
							환영.setVisible(true);
							if (loginPosition.equals("관리자")) {
								WriteEventLog("관리자, "+ID_input.getText()+" 로그인");
								회원관리.setEnabled(true);
								회원관리.setVisible(true);
							}
							else if (loginPosition.equals("사용자")) {
								WriteEventLog("사용자, "+ID_input.getText()+" 로그인");
								회원탈퇴.setEnabled(true);
								회원탈퇴.setVisible(true);
							}
							
							//새로고침
							for (int i=0;i<60;i++)
								panel_미디어[i].removeAll();
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
						else //틀린 비밀번호 입력
							JOptionPane.showMessageDialog(null, "일치하지 않는 PW입니다. \n다시 입력해주세요.","Login_Fail",JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			확인.setBounds(188, 430, 108, 38);
			contentPane3.add(확인);
			
			ImageIcon logo = new ImageIcon("src\\images\\login_logo.PNG"); //로고
			Image logo_img = logo.getImage();
			Image logo_re = logo_img.getScaledInstance(280, 80, Image.SCALE_SMOOTH);
			JLabel logo_L = new JLabel(new ImageIcon(logo_re));
			logo_L.setBounds(30,10,280,80);
			contentPane3.add(logo_L);
		}
	}
	
	private class upload_screen extends JFrame{ //미디어 등록 화면
		public upload_screen() {
			setBounds(650, 300, 650, 450);
			setResizable(false);
			contentPane4 = new JPanel();
			contentPane4.setBackground(Color.WHITE);
			contentPane4.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane4.setLayout(null);
			setContentPane(contentPane4);
			
			JLabel 제목 = new JLabel("\uBBF8\uB514\uC5B4 \uC81C\uBAA9");
			제목.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			제목.setBounds(60, 102, 81, 15);
			contentPane4.add(제목);
			
			title_input = new JTextField();
			title_input.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			title_input.setBounds(60, 127, 196, 21);
			contentPane4.add(title_input);
			title_input.setColumns(10);
			
			ImageIcon logo = new ImageIcon("src\\images\\upload_logo.PNG"); //로고
			Image logo_img = logo.getImage();
			Image logo_re = logo_img.getScaledInstance(280, 80, Image.SCALE_SMOOTH);
			JLabel logo_L = new JLabel(new ImageIcon(logo_re));
			logo_L.setBounds(175,10,280,80);
			contentPane4.add(logo_L);
			
			JLabel 이미지 = new JLabel("\uB300\uD45C \uC774\uBBF8\uC9C0");
			이미지.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			이미지.setBounds(60, 177, 81, 15);
			contentPane4.add(이미지);
			
			Image_fopen = new FileDialog(this, "파일 열기", FileDialog.LOAD);
			
			JButton file_open = new JButton("파일 열기");
			file_open.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
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
			thumbnail_input.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			thumbnail_input.setColumns(10);
			thumbnail_input.setBounds(60, 202, 196, 21);
			contentPane4.add(thumbnail_input);
			
			JLabel 유형 = new JLabel("\uC720\uD615");
			유형.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			유형.setBounds(60, 249, 81, 15);
			contentPane4.add(유형);
			
			JComboBox type_input = new JComboBox();
			type_input.setModel(new DefaultComboBoxModel(new String[] {"\uB3D9\uC601\uC0C1", "\uC74C\uC545", "\uC18C\uC124", "\uB9CC\uD654"}));
			type_input.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			type_input.setBounds(60, 274, 196, 23);
			contentPane4.add(type_input);
			
			JComboBox category_input = new JComboBox();
			category_input.setModel(new DefaultComboBoxModel(new String[] {"\uAC8C\uC784", "\uC2A4\uD3EC\uCE20", "\uD559\uC2B5", "\uC74C\uC2DD"}));
			category_input.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			category_input.setBounds(362, 125, 196, 23);
			contentPane4.add(category_input);
			
			JLabel 카테고리 = new JLabel("\uCE74\uD14C\uACE0\uB9AC");
			카테고리.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			카테고리.setBounds(362, 100, 81, 15);
			contentPane4.add(카테고리);
			
			JComboBox donation_input = new JComboBox();
			donation_input.setModel(new DefaultComboBoxModel(new String[] {"\uAC00\uB2A5", "\uBD88\uAC00\uB2A5"}));
			donation_input.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			donation_input.setBounds(362, 202, 196, 23);
			contentPane4.add(donation_input);
			
			JLabel 기부 = new JLabel("\uAE30\uBD80");
			기부.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			기부.setBounds(362, 177, 81, 15);
			contentPane4.add(기부);
			
			JSpinner time_input_01 = new JSpinner();
			time_input_01.setModel(new SpinnerNumberModel(0, 0, 59, 1));
			time_input_01.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			time_input_01.setBounds(430, 276, 56, 22);
			contentPane4.add(time_input_01);
			
			JSpinner time_input_02 = new JSpinner();
			time_input_02.setModel(new SpinnerNumberModel(0, 0, 59, 1));
			time_input_02.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			time_input_02.setBounds(362, 276, 56, 22);
			contentPane4.add(time_input_02);
			
			JLabel 시간 = new JLabel("\uC7AC\uC0DD \uC2DC\uAC04");
			시간.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			시간.setBounds(362, 250, 81, 15);
			contentPane4.add(시간);
			
			JButton 취소 = new JButton("\uCDE8\uC18C");
			취소.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			취소.setBackground(new Color(135, 206, 235));
			취소.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			취소.setBounds(150, 342, 108, 38);
			contentPane4.add(취소);
			
			JButton 등록 = new JButton("\uB4F1\uB85D");
			등록.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			등록.setBackground(new Color(135, 206, 235));
			등록.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (title_input.getText().isEmpty()||thumbnail_input.getText().isEmpty()) //비어있는 곳이 있을 경우
						JOptionPane.showMessageDialog(null, "입력되지 않은 항목이 있습니다.\n다시 확인해주세요.","Upload_Fail",JOptionPane.ERROR_MESSAGE);
					else if (CheckTumbnail(thumbnail_input.getText())==false) //입력 파일이 이미지가 아닐 경우
						JOptionPane.showMessageDialog(null, "대표 이미지 입력이 잘못됐습니다.\n이미지 파일을 입력해주세요.","Upload_Fail",JOptionPane.ERROR_MESSAGE);
					else { 
						if (type_input.getSelectedItem().toString().equals("동영상")||type_input.getSelectedItem().toString().equals("음악")) { //Video_Music
							if (time_input_01.getValue().toString().equals("0")&&time_input_02.getValue().toString().equals("0")) //재생시간이 입력되지 않았을 경우
								JOptionPane.showMessageDialog(null, "재생 시간이 입력되지 않았습니다.\n재생 시간을 입력해주세요.","Upload_Fail",JOptionPane.ERROR_MESSAGE);
							else {
								String time = CheckTime(time_input_01.getValue().toString(), time_input_02.getValue().toString());
								String file_name = Image_fopen_name;
								int pos = file_name.lastIndexOf(".");
								String temp = file_name.substring(pos+1);
								String thumbnail = CopyThumbnail(thumbnail_input.getText(), temp);
								if (ExistMedia(thumbnail,type_input.getSelectedItem().toString(), category_input.getSelectedItem().toString(),donation_input.getSelectedItem().toString(),time,title_input.getText(),loginID)) //이미 존재하는 미디어
									JOptionPane.showMessageDialog(null, "이미 등록된 미디어입니다.\n다시 확인해주세요.","Upload_Fail",JOptionPane.ERROR_MESSAGE);
								else { //성공
									WriteEventLog(loginID+" 미디어 \""+title_input.getText()+"\" 등록");
									Video_Music_List.add(new Video_Music(loginID,title_input.getText(),thumbnail,type_input.getSelectedItem().toString(),category_input.getSelectedItem().toString(),
											donation_input.getSelectedItem().toString(),time,0,0,0));
									mediaCount+=1;
									
									//미디어 갱신 함수
									updateMedia(thumbnail, type_input.getSelectedItem().toString(),category_input.getSelectedItem().toString(),donation_input.getSelectedItem().toString(), time,
											title_input.getText(), loginID, mediaCount-1,0,0,0);
									
									JOptionPane.showMessageDialog(null, "미디어 등록에 성공하셨습니다.\n축하드립니다!","Sign_up_Success",JOptionPane.INFORMATION_MESSAGE);
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
							if (ExistMedia(thumbnail,type_input.getSelectedItem().toString(), category_input.getSelectedItem().toString(),donation_input.getSelectedItem().toString(),"Novel_Comic",title_input.getText(),loginID)) //이미 존재하는 미디어
								JOptionPane.showMessageDialog(null, "이미 등록된 미디어입니다.\n다시 확인해주세요.","Upload_Fail",JOptionPane.ERROR_MESSAGE);
							else { //성공
								WriteEventLog(loginID+" 미디어 \""+title_input.getText()+"\" 등록");
								Novel_Comic_List.add(new Novel_Comic(loginID,title_input.getText(),thumbnail,type_input.getSelectedItem().toString(),category_input.getSelectedItem().toString(),
										donation_input.getSelectedItem().toString(),0,0,-1));
								mediaCount+=1;
	
								//미디어 갱신 함수							
								updateMedia(thumbnail, type_input.getSelectedItem().toString(),category_input.getSelectedItem().toString(),donation_input.getSelectedItem().toString(), "Novel_Comic",
										title_input.getText(), loginID, mediaCount-1,0,0,-1);
	
								JOptionPane.showMessageDialog(null,"미디어 등록에 성공하셨습니다.\n축하드립니다!","Sign_up_Success",JOptionPane.INFORMATION_MESSAGE);
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
			등록.setBounds(364, 342, 108, 38);
			contentPane4.add(등록);
		}
	}
	
	public class manager_screen extends JFrame { //회원 관리 화면
		public manager_screen() {
			setBounds(800, 250, 450, 300);
			setResizable(false);
			contentPane5 = new JPanel();
			contentPane5.setBackground(Color.WHITE);
			contentPane5.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane5.setLayout(null);
			setContentPane(contentPane5);
			
			manager_input = new JTextField();
			manager_input.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			manager_input.setBounds(80, 147, 266, 29);
			contentPane5.add(manager_input);
			manager_input.setColumns(10);
			
			JButton 취소 = new JButton("\uCDE8\uC18C");
			취소.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			취소.setBackground(new Color(135, 206, 235));
			취소.setBounds(80, 197, 97, 29);
			취소.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			contentPane5.add(취소);
			
			JButton 확인 = new JButton("\uD655\uC778");
			확인.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			확인.setBackground(new Color(135, 206, 235));
			확인.setBounds(249, 197, 97, 29);
			contentPane5.add(확인);
			확인.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (NotExistUserID(manager_input.getText()))
						JOptionPane.showMessageDialog(null, "존재하지 않는 사용자입니다.\n사용자ID를 다시 확인해주세요.", "Manage_Fail",JOptionPane.ERROR_MESSAGE);
					else if (CheckPosition(manager_input.getText())) {
						JOptionPane.showMessageDialog(null, "관리자 ID는 삭제할 수 없습니다.\nID를 다시 확인해주세요.", "Manage_Fail",JOptionPane.ERROR_MESSAGE);
					}
					else {
						int result = JOptionPane.showConfirmDialog(null, "정말 해당 사용자를 삭제하시겠습니까?","manage",JOptionPane.YES_NO_OPTION);
						if (result==JOptionPane.YES_OPTION) {
							WriteEventLog("관리자, "+loginID+"가 사용자, "+manager_input.getText()+" 회원 삭제");
							
							DelUserInfo(manager_input.getText());
							
							for (int i=0;i<60;i++)
								panel_미디어[i].removeAll();
							panel_03.repaint();
							for (int i=0; i<Video_Music_List.size(); i++)
								updateMedia(Video_Music_List.get(i).getMediaAllInfo()[2],Video_Music_List.get(i).getMediaAllInfo()[3],Video_Music_List.get(i).getMediaAllInfo()[4],Video_Music_List.get(i).getMediaAllInfo()[5],
										Video_Music_List.get(i).getMediaAllInfo()[6],Video_Music_List.get(i).getMediaAllInfo()[1],Video_Music_List.get(i).getMediaAllInfo()[0],i,Video_Music_List.get(i).getMediaNumInfo()[0],Video_Music_List.get(i).getMediaNumInfo()[1],Video_Music_List.get(i).getMediaNumInfo()[2]);
							for (int i=0; i<Novel_Comic_List.size();i++)
								updateMedia(Novel_Comic_List.get(i).getMediaAllInfo()[2],Novel_Comic_List.get(i).getMediaAllInfo()[3],Novel_Comic_List.get(i).getMediaAllInfo()[4],Novel_Comic_List.get(i).getMediaAllInfo()[5],
										"Novel_Comic",Novel_Comic_List.get(i).getMediaAllInfo()[1],Novel_Comic_List.get(i).getMediaAllInfo()[0],i+Video_Music_List.size(),Novel_Comic_List.get(i).getMediaNumInfo()[0],Novel_Comic_List.get(i).getMediaNumInfo()[1],Novel_Comic_List.get(i).getMediaNumInfo()[2]);
							
							JOptionPane.showMessageDialog(null, "회원삭제가 완료되었습니다.","Manage_Success",JOptionPane.INFORMATION_MESSAGE);
						}
						else if (result==JOptionPane.NO_OPTION) {
							JOptionPane.showMessageDialog(null, "회원삭제가 취소되었습니다!","Manage_Cancel",JOptionPane.INFORMATION_MESSAGE);
						}
						dispose();
					}
				}
			});
			
			JLabel 입력 = new JLabel("삭제하고자 하는 사용자의 ID를 입력해주세요!");
			입력.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
			입력.setBounds(65, 122, 295, 15);
			contentPane5.add(입력);
			
			ImageIcon logo = new ImageIcon("src\\images\\manager_logo.PNG"); //로고
			Image logo_img = logo.getImage();
			Image logo_re = logo_img.getScaledInstance(280, 80, Image.SCALE_SMOOTH);
			JLabel logo_L = new JLabel(new ImageIcon(logo_re));
			logo_L.setBounds(80,16,280,80);
			contentPane5.add(logo_L);
		}
	}
	
	public class media_Info_screen extends JFrame implements ActionListener, Runnable{ //미디어 상세 정보 화면
		//전역
		private JProgressBar p; 
		JLabel 시간_progress;
		String input_time;
		int hour=0;
		int min=0;
		int val_progress=0;
		Thread t;
		JButton [] player_B = new JButton[3];
		int 댓글_num = 0;
		
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
				
				ImageIcon logo = new ImageIcon("src\\images\\logo.PNG"); //로고
				Image logo_img = logo.getImage();
				Image logo_re = logo_img.getScaledInstance(355, 60, Image.SCALE_SMOOTH);
				JLabel logo_L = new JLabel(new ImageIcon(logo_re));
				logo_L.setBounds(20,50,375, 65);
				I_panel_01.add(logo_L);
				
				I_panel_02.setBackground(new Color(135, 206, 235));
				I_panel_02.setBounds(0, 89, 728, 572);
				I_panel_02.setLayout(null);
				contentPane6.add(I_panel_02);
				
				ImageIcon thumbnail_I = new ImageIcon(thumbnail); //썸네일
				Image thumbnail_img = thumbnail_I.getImage();
				Image thumbnail_re = thumbnail_img.getScaledInstance(680,395, Image.SCALE_SMOOTH);
				JLabel thumbnail_L = new JLabel(new ImageIcon(thumbnail_re));
				thumbnail_L.setBounds(25,33,680, 395);
				I_panel_02.add(thumbnail_L);
				
				ImageIcon [] func = new ImageIcon[2];
				
				if (time.equals("Novel_Comic")) {
					func[0] = new ImageIcon("src\\images\\unbookmark.png"); //노책갈피
					func[1] = new ImageIcon("src\\images\\bookmark.png"); //책갈피
				}
				else {
					func[0] = new ImageIcon("src\\images\\volume.png"); //소리
					func[1] = new ImageIcon("src\\images\\mute.png"); //음소거
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
				
				func_B[0].addActionListener(new ActionListener() { //노책갈피 //소리
					public void actionPerformed(ActionEvent e) {
						if (time.equals("Novel_Comic")) {
							AddBookmarkUser(thumbnail, loginID);
							panel_미디어[location].removeAll();
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
				
				func_B[1].addActionListener(new ActionListener() { //책갈피 //음소거 //확인
					public void actionPerformed(ActionEvent e) {
						if (time.equals("Novel_Comic")) {
							DelBookmarkUser(thumbnail, loginID);
							panel_미디어[location].removeAll();
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
							WriteEventLog(loginID+" 미디어 \""+title+"\" 재생");
							
							player_B[0].setEnabled(false);
							player_B[0].setVisible(false);
							player_B[1].setEnabled(true);
							player_B[1].setVisible(true);
							I_panel_02.repaint();
							
							Video_Music_List.get(media_index).addPlay();
							WriteVideo_MusicInfo();
							panel_미디어[location].removeAll();
							updateMedia(thumbnail, type, category, donation, time, title, id, location, Video_Music_List.get(media_index).getMediaNumInfo()[0],
									Video_Music_List.get(media_index).getMediaNumInfo()[1], Video_Music_List.get(media_index).getMediaNumInfo()[2]);
							panel_03.repaint();

							etc_num+=1;
							재생.setText(Integer.toString(etc_num));
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
							시간_progress.setText("00:00/"+input_time);
						}
					});
				}

				I_panel_03.setBackground(new Color(135, 206, 235));
				I_panel_03.setBounds(734, 89, 250, 219);
				I_panel_03.setLayout(null);
				contentPane6.add(I_panel_03);
				
				JLabel 제목 = new JLabel(title);
				제목.setFont(new Font("한컴 고딕", Font.BOLD, 20));
				제목.setBounds(12, 10, 226, 20);
				I_panel_03.add(제목);
				
				JLabel 작성자 = new JLabel(id);
				작성자.setFont(new Font("한컴 고딕", Font.BOLD, 17));
				작성자.setBounds(12, 37, 185, 20);
				I_panel_03.add(작성자);
				
				기부 = new JLabel(Integer.toString(donation_num));
				기부.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
				기부.setBounds(50, 110, 81, 20);
				I_panel_03.add(기부);
				
				추천 = new JLabel(Integer.toString(good_num));
				추천.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
				추천.setBounds(50, 145, 81, 20);
				I_panel_03.add(추천);
				
				재생 = new JLabel(Integer.toString(etc_num));
				재생.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
				재생.setBounds(50, 180, 81, 20);
				I_panel_03.add(재생);
				
				if (!time.equals("Novel_Comic")) {
					시간_progress = new JLabel("00:00/"+time);
					시간_progress.setFont(new Font("한컴 고딕", Font.PLAIN, 20));
					시간_progress.setBounds(580, 450, 150, 20);
					I_panel_02.add(시간_progress);
				}
				
				ImageIcon[] icon_미디어 = new ImageIcon[5];
				JLabel [] icon_미디어_L = new JLabel[3];
				JButton [] icon_미디어_B = new JButton[2];
				
				String image_미디어 [] = GetImageInfo(type,category, donation);
				
				for (int i=0;i<2;i++)
					icon_미디어[i]=new ImageIcon(image_미디어[i]); //유형, 카테고리
				icon_미디어[2]=new ImageIcon("src\\images\\play.png");
				icon_미디어[3]=new ImageIcon(image_미디어[2]); //기부

				icon_미디어[4]=new ImageIcon("src\\images\\good.png"); //추천
				
				for (int  i=0; i<3;i++) {
					Image icon_미디어_img = icon_미디어[i].getImage();
					Image icon_미디어_re = icon_미디어_img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
					icon_미디어_L[i]=new JLabel(new ImageIcon(icon_미디어_re));
					I_panel_03.add(icon_미디어_L[i]);
				}
				icon_미디어_L[0].setBounds(13,70,25,25);
				icon_미디어_L[1].setBounds(48,70,25,25);
				icon_미디어_L[2].setBounds(13,175,25,25);
				
				for (int i=0;i<2;i++) {
					Image icon_미디어_img = icon_미디어[i+3].getImage();
					Image icon_미디어_re = icon_미디어_img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
					icon_미디어_B[i]=new JButton(new ImageIcon(icon_미디어_re));
					icon_미디어_B[i].setBorderPainted(false);
					icon_미디어_B[i].setContentAreaFilled(false);
					icon_미디어_B[i].setFocusPainted(false);
					I_panel_03.add(icon_미디어_B[i]);	
				}
				icon_미디어_B[0].setBounds(13,105,25,25);
				icon_미디어_B[1].setBounds(13,140,25,25);
				
				if(donation.equals("가능")) {
					icon_미디어_B[0].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (time.equals("Novel_Comic")) {
								Novel_Comic_List.get(media_index).addDonation();
								WriteNovel_ComicInfo();
								panel_미디어[location].removeAll();
								updateMedia(thumbnail, type, category, donation, time, title, id, location,Novel_Comic_List.get(media_index).getMediaNumInfo()[0], 
									Novel_Comic_List.get(media_index).getMediaNumInfo()[1], Novel_Comic_List.get(media_index).getMediaNumInfo()[2]);
							}
							else {
								Video_Music_List.get(media_index).addDonation();
								WriteVideo_MusicInfo();
								panel_미디어[location].removeAll();
								updateMedia(thumbnail, type, category, donation, time, title, id, location, Video_Music_List.get(media_index).getMediaNumInfo()[0],
										Video_Music_List.get(media_index).getMediaNumInfo()[1], Video_Music_List.get(media_index).getMediaNumInfo()[2]);
							}
							WriteEventLog(loginID+" 미디어 \""+title+"\" 기부");
							donation_num+=100;
							기부.setText(Integer.toString(donation_num));
							I_panel_03.repaint();
							panel_03.repaint();
						}
					});
				}
				else {
					기부.setEnabled(false);
					기부.setVisible(false);
				}
				
				icon_미디어_B[1].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (time.equals("Novel_Comic")) {
							Novel_Comic_List.get(media_index).addGood();
							WriteNovel_ComicInfo();
							panel_미디어[location].removeAll();
							updateMedia(thumbnail, type, category, donation, time, title, id, location,Novel_Comic_List.get(media_index).getMediaNumInfo()[0], 
								Novel_Comic_List.get(media_index).getMediaNumInfo()[1], Novel_Comic_List.get(media_index).getMediaNumInfo()[2]);
						}
						else {
							Video_Music_List.get(media_index).addGood();
							WriteVideo_MusicInfo();
							panel_미디어[location].removeAll();
							updateMedia(thumbnail, type, category, donation, time, title, id, location, Video_Music_List.get(media_index).getMediaNumInfo()[0],
									Video_Music_List.get(media_index).getMediaNumInfo()[1], Video_Music_List.get(media_index).getMediaNumInfo()[2]);
						}
						WriteEventLog(loginID+" 미디어 \""+title+"\" 추천");
						good_num+=1;
						추천.setText(Integer.toString(good_num));
						I_panel_03.repaint();
						panel_03.repaint();
					}
				});
				
				if (time.equals("Novel_Comic")) {
					icon_미디어_L[2].setEnabled(false);
					icon_미디어_L[2].setVisible(false);
					재생.setEnabled(false);
					재생.setVisible(false);
				}
				
				JPanel I_panel_04 = new JPanel();
				I_panel_04.setBackground(new Color(135, 206, 235));
				I_panel_04.setBounds(734, 314, 250, 347);
				I_panel_04.setLayout(null);
				contentPane6.add(I_panel_04);
				
				JLabel 댓글 = new JLabel("\uB313\uAE00");
				댓글.setBackground(new Color(255, 255, 255));
				댓글.setHorizontalAlignment(SwingConstants.CENTER);
				댓글.setFont(new Font("한컴 고딕", Font.BOLD, 25));
				댓글.setBounds(0, 0, 250, 30);
				I_panel_04.add(댓글);
				
				JPanel I_panel_04_01 = new JPanel();
				I_panel_04_01.setBackground(SystemColor.control);
				I_panel_04_01.setLayout(new GridLayout(30,1,3,3));
				I_panel_04.add(I_panel_04_01);
				
				JPanel [] I_panel_댓글 = new JPanel [30];
				for (int i=0;i<30;i++) {
					I_panel_댓글[i] = new JPanel();
					I_panel_댓글[i].setLayout(null);
					I_panel_댓글[i].setBackground(Color.white);
					I_panel_댓글[i].setPreferredSize(new Dimension(100,60));
					I_panel_댓글[i].setMaximumSize(getPreferredSize());
					I_panel_댓글[i].setMinimumSize(getPreferredSize());
					I_panel_04_01.add(I_panel_댓글[i]);
				}
				JScrollPane scroll = new JScrollPane(I_panel_04_01);
				scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scroll.setBounds(7, 35, 238, 240);
				I_panel_04.add(scroll);
				
				JTextField input_댓글 = new JTextField();
				input_댓글.setFont(new Font("한컴 고딕", Font.PLAIN, 13));
				input_댓글.setBounds(7, 285, 169, 54);
				input_댓글.setColumns(10);
				I_panel_04.add(input_댓글);

				JButton 댓글_B = new JButton("\uB313\uAE00");
				댓글_B.setBackground(SystemColor.control);
				댓글_B.setFont(new Font("한컴 고딕", Font.PLAIN, 12));
				댓글_B.setBounds(181, 285, 63, 54);
				댓글_B.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (input_댓글.getText().isEmpty())
							JOptionPane.showMessageDialog(null, "댓글 내용을 입력한 후 등록을 진행해주세요.","Comment_Fail",JOptionPane.ERROR_MESSAGE);
						else {
							if (댓글_num<30) {
								WriteEventLog(loginID+" 미디어 \""+title+"\" 댓글 작성");
								JLabel 댓글_작성자 = new JLabel("작성자 : "+loginID);
								JLabel 댓글_내용 = new JLabel(input_댓글.getText());
								댓글_작성자.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
								댓글_내용.setFont(new Font("한컴 고딕", Font.PLAIN, 15));
								댓글_작성자.setBounds(5,5, 100, 14);
								댓글_내용.setBounds(5,30, 220, 14);
								I_panel_댓글[댓글_num].add(댓글_작성자);
								I_panel_댓글[댓글_num].add(댓글_내용);
								I_panel_04.repaint();
								댓글_num++;
							}
							else {
								JOptionPane.showMessageDialog(null, "댓글을 작성할 공간이 없습니다.\n죄송합니다.","Comment_Fail",JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				});
				I_panel_04.add(댓글_B);
				
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
					시간_progress.setText(hour_s+":"+min_s+"/"+input_time);
					I_panel_02.repaint();
					Thread.sleep(10); //0.01초
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