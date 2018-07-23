package src;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

/*
    2.键盘映射
 */
public class gui extends JFrame {

    DecimalFormat df = new DecimalFormat("###.####");
    MyListener ml = new MyListener();

    String s = "标准型";
    JLabel finallnum;

    JLabel passnum;
    double sopr1 = 0;
    double result = 0;
    int stacode = 0;
    boolean cal_end = false;

    String history[] = new String[10];
    int his_i= 0;

//初始化
    public gui() {
        setLayout(new BorderLayout());
        setMenuBar();
        setTopBar();
//        setFunc();
        setKeyBoard();

        setTitle("Calculator");
        setSize(341, 530);
        setResizable(false);
        setLocation(200, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //    上栏
    public void setTopBar() {
        JPanel jp = new JPanel();
//        jp.setBackground(new Color(241,241,241));
        jp.setPreferredSize(new Dimension(0, 150));
        jp.setLayout(new BorderLayout());

        //状态框
        JPanel jpsta = new JPanel();
//        jpsta.setBackground(new Color(241,241,241));
        jpsta.setPreferredSize(new Dimension(0, 50));
        jpsta.setLayout(new FlowLayout(FlowLayout.LEFT));
        //状态
        JLabel jlSta = new JLabel("当前计算器类型为: " + s + "                         ");
        jlSta.setFont(new Font("微软雅黑", 0, 12));

        //历史记录
        JButton jbHistory = new JButton("历史记录");
        jbHistory.setContentAreaFilled(false);


        jbHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JDialog jd = new JDialog();
                jd.setTitle("历史记录");
                jd.setBounds(280,230,200,400);
                jd.setVisible(true);
                jd.setResizable(false);

//                System.out.println(history[0]);
//                System.out.println(his_i);



                JTextArea jl = new JTextArea();
//                System.out.println(jl.getText());
                jl.setEditable(false);


                for (int i = 0; i<his_i;i++){

                    jl.setText( jl.getText() +"\n"+ "第"+(i+1) + "次计算： " +history[i]);

                    jd.add(jl);
                }
            }
        });


        //去焦点
        jbHistory.setFocusPainted(false);
        jbHistory.addActionListener(ml);
        jbHistory.setFont(new Font("微软雅黑", 0, 12));

        jpsta.add(jlSta);
        jpsta.add(jbHistory);
        //显示框
        JPanel jpCon = new JPanel();
        jpCon.setPreferredSize(new Dimension(0, 100));
        jpCon.setLayout(new GridLayout(2, 1));

        //主
        finallnum = new JLabel();
        finallnum.setText("0");
        finallnum.setBackground(new Color(241,241,241));
        finallnum.setHorizontalAlignment(SwingConstants.RIGHT);
        finallnum.setFont(new Font("微软雅黑", 0, 48));
        //副
        passnum = new JLabel("");
        passnum.setHorizontalAlignment(SwingConstants.RIGHT);
        passnum.setFont(new Font("微软雅黑", 0, 18));
        passnum.setForeground(new Color(112,128,144));

        jpCon.add(passnum);
        jpCon.add(finallnum);

        jp.add(jpsta, BorderLayout.NORTH);
        jp.add(jpCon, BorderLayout.CENTER);

        add(jp, BorderLayout.NORTH);
    }

    //    功能栏
    public void setFunc() {
        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 0));
        String sfunc[] = {"MC", "MR", "M+", "M-", "MS", "M"};
        for (int i = 0; i < 6; i++) {
            JButton jb = new JButton(sfunc[i]);
            jb.setContentAreaFilled(false);
            //去边框
            jb.setBorderPainted(false);
            //去焦点
            jb.setFocusPainted(false);
            jb.addActionListener(ml);
            jp.add(jb);
        }
        add(jp, BorderLayout.CENTER);
    }

    //键盘栏
    public void setKeyBoard() {
        JPanel jp = new JPanel();
//        jp.setBackground(new Color(190,189,188));
        jp.setLayout(new GridLayout(6, 4, 5, 5));


        String s[] = {"%", "√", "平方", "倒数", "CE", "C", "←", "÷", "7", "8", "9", "X", "4", "5", "6", "-", "1", "2", "3", "+", "±", "0", ".", "="};
        for (int i = 0; i <= 23; i++) {
            JButton jb = new JButton(s[i]);
            jb.addActionListener(ml);
            jb.setFont(new Font("微软雅黑", 0, 16));
            jb.setBackground(new Color(241,241,241));


            if(     s[i] == "7"||
                    s[i] == "8"||
                    s[i] == "9"||
                    s[i] == "4"||
                    s[i] == "5"||
                    s[i] == "6"||
                    s[i] == "1"||
                    s[i] == "2"||
                    s[i] == "3"||
                    s[i] == "0"
                    ){
                jb.setBackground(Color.WHITE);
                jb.setFont(new Font("微软雅黑", 1, 19));
            }


//            jb.setBackground(Color.WHITE);
            //去边框
            jb.setBorderPainted(false);
            //去焦点
            jb.setFocusPainted(false);
            jp.add(jb);
        }

        jp.setPreferredSize(new Dimension(0, 310));
        add(jp, BorderLayout.SOUTH);
    }

//剪贴板
    public static void setClipboardString(String text) {
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 封装文本内容
        Transferable trans = new StringSelection(text);
        // 把文本内容设置到系统剪贴板
        clipboard.setContents(trans, null);
    }
//菜单栏
    public void setMenuBar() {
        //菜单栏
        JMenuBar menu;
        JMenu File,choose,edit;
        JMenuItem about, standard, science,shutdown,copy;

        //菜单栏设置
        menu = new JMenuBar();
        File = new JMenu("文件");
        edit  = new JMenu("编辑");
        about = new JMenuItem("关于");
        shutdown = new JMenuItem("关闭");
        copy = new JMenuItem("复制结果");
        choose = new JMenu("类型");
        standard = new JMenuItem("标准型");
        science = new JMenuItem("时间型");

        File.setFont(new Font("微软雅黑",0,15));
        edit .setFont(new Font("微软雅黑",0,15));
        about.setFont(new Font("微软雅黑",0,15));
        shutdown.setFont(new Font("微软雅黑",0,15));
        copy.setFont(new Font("微软雅黑",0,15));
        choose.setFont(new Font("微软雅黑",0,15));
        standard.setFont(new Font("微软雅黑",0,15));
        science.setFont(new Font("微软雅黑",0,15));


        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setClipboardString(finallnum.getText());
                JDialog jd = new JDialog();
                jd.setVisible(true);
                jd.setBounds(200,200,200,100);
                jd.setResizable(false);
                JLabel jl = new JLabel("复制成功");
                jl.setFont(new Font("微软雅黑",0,15));
                jl.setHorizontalAlignment(SwingConstants.CENTER);
                jd.add(jl);

            }
        });

        shutdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog jd = new JDialog();
                jd.setResizable(false);
                JPanel  jp = new JPanel();
                jp.setLayout(new BorderLayout());
                JLabel jl1 =new JLabel("2018.06.28");
                jl1.setPreferredSize(new Dimension(350,80));
                jl1.setHorizontalAlignment(SwingConstants.CENTER);
                JLabel jl2 = new JLabel("Made By Yunze Li");
                JLabel jl3 = new JLabel("E-mail:arthur.yunze@gmail.com");

                jl1.setFont(new Font("微软雅黑",0,15));
                jl2.setFont(new Font("微软雅黑",0,15));
                jl3.setFont(new Font("微软雅黑",0,15));


                jl2.setHorizontalAlignment(SwingConstants.CENTER);
                jl3.setHorizontalAlignment(SwingConstants.CENTER);
                jl3.setPreferredSize(new Dimension(350,80));
                jd.setTitle("关于");
                jd.setBounds(200,200,350,250);

                jp.add(jl1,BorderLayout.NORTH);
                jp.add(jl2,BorderLayout.CENTER);
                jp.add(jl3,BorderLayout.SOUTH);
                jd.add(jp);

                jd.setVisible(true);
            }
        });


        File.add(choose);
        File.add(shutdown);
            choose.add(standard);
            choose.add(science);
        edit.add(copy);


        menu.add(File);
        menu.add(edit);
        menu.add(about);
        setJMenuBar(menu);
    }




        //计算
    class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String getstr = e.getActionCommand();
            //按数字键
            if (
                    getstr.equals("0") ||
                            getstr.equals("1") ||
                            getstr.equals("2") ||
                            getstr.equals("3") ||
                            getstr.equals("4") ||
                            getstr.equals("5") ||
                            getstr.equals("6") ||
                            getstr.equals("7") ||
                            getstr.equals("8") ||
                            getstr.equals("9")) {


                if (cal_end) {
                    cal_end = false;
                    finallnum.setText("0");
                    if (finallnum.getText() == "0"||finallnum.getText().indexOf("∞")!=-1 ) {
                        finallnum.setText(getstr);
                    } else {
                        finallnum.setText(finallnum.getText() + getstr);
                    }
                } else {
                    if (finallnum.getText() == "0"||finallnum.getText() == "∞") {
                        finallnum.setText(getstr);
                    } else {
                        finallnum.setText(finallnum.getText() + getstr);
                    }
                }

//对de
//                    if(finallnum.getText() == "0"){
//                        finallnum.setText(getstr);
//                    }else{
//                        finallnum.setText(finallnum.getText() + getstr);
//                    }


                //按操作键
            } else if (e.getActionCommand().equals("CE")) {
                finallnum.setText("0");
                cal_end = false;
            } else if (e.getActionCommand().equals("←")) {
                String tuige = finallnum.getText();
                if (tuige.length() == 1) {
                    finallnum.setText("0");
                } else {
                    tuige = tuige.substring(0, tuige.length() - 1);
                    finallnum.setText(tuige);
                }
            } else if (e.getActionCommand().equals("√")) {
                double a = Double.valueOf(finallnum.getText());
                result = Math.sqrt(a);
                finallnum.setText(df.format(result) + "");

            } else if (e.getActionCommand().equals("平方")) {
                double a = Double.valueOf(finallnum.getText());
                result = a * a;
                finallnum.setText(df.format(result) + "");
            } else if (e.getActionCommand().equals("倒数")) {
                double a = Double.valueOf(finallnum.getText());
                result = 1 / a;
                finallnum.setText(df.format(result) + "");

            } else if (e.getActionCommand().equals("C")) {
                finallnum.setText("0");
                passnum.setText("");
                //双目运算
            } else if (e.getActionCommand().equals("%")) {
                stacode = 4;
                sopr1 = Double.valueOf(finallnum.getText());
                result = result % sopr1;
                passnum.setText(passnum.getText()+df.format(sopr1) + "%");
                finallnum.setText("0");

                //可能需要添加的代码
//                    cal_end = true;

            } else if (e.getActionCommand().equals("+")) {
                stacode = 0;
                sopr1 = Double.valueOf(finallnum.getText());
                result = result + sopr1;
//                System.out.println(sopr1);
                passnum.setText(passnum.getText()+df.format(sopr1) + "+");
                finallnum.setText("0");
            } else if (e.getActionCommand().equals("-")) {
                stacode = 1;
                sopr1 = Double.valueOf(finallnum.getText());
                result = result - sopr1;
//                System.out.println(sopr1);
                passnum.setText(passnum.getText()+df.format(sopr1) + "-");
                finallnum.setText("0");
            } else if (e.getActionCommand().equals("X")) {
                stacode = 2;
                sopr1 = Double.valueOf(finallnum.getText());
                result = result * sopr1;
//                System.out.println(sopr1);
                passnum.setText(passnum.getText()+df.format(sopr1) + "X");
                finallnum.setText("0");
            } else if (e.getActionCommand().equals("÷")) {
                stacode = 3;
                sopr1 = Double.valueOf(finallnum.getText());
                result = result / sopr1;
//                System.out.println(sopr1);
                passnum.setText(passnum.getText()+df.format(sopr1) + "÷");
                finallnum.setText("0");
            } else if (getstr.equals("=")) {
                if(finallnum.getText().indexOf("∞")!=-1) finallnum.setText("0");
                double sopr2 = Double.valueOf(finallnum.getText());
//                    System.out.println(sopr2);
//                    System.out.println(sopr1);
                switch (stacode) {
                    case 0:
                        result = sopr1 + sopr2;
                        break;
                    case 1:
                        result = sopr1 - sopr2;
                        break;
                    case 2:
                        result = sopr1 * sopr2;
                        break;
                    case 3:
                        result = sopr1 / sopr2;
                        break;
                    case 4:
                        result = sopr1 % sopr2;
                        break;
                }

                //历史记录板
                history[his_i] = passnum.getText()+df.format(sopr2)+ "=" + df.format(result);
                his_i++;


                passnum.setText("");
                finallnum.setText(df.format(result) + "");
                sopr1 = 0;
                cal_end = true;

            }  else if (e.getActionCommand().equals(".")) {
                if (finallnum.getText().indexOf(".") != -1) {
                    finallnum.setText(finallnum.getText());
                } else {
                    finallnum.setText(finallnum.getText() + ".");
                }
            } else if (e.getActionCommand().equals("±")) {
                if (finallnum.getText() == "0") {
                    finallnum.setText(finallnum.getText());
                } else if (Double.valueOf(finallnum.getText()) < 0) {
                    double a = Double.valueOf(finallnum.getText());
                    finallnum.setText(df.format(Math.abs(a)) + "");
                } else if (Double.valueOf(finallnum.getText()) > 0) {
                    finallnum.setText(  df.format(0 - Double.valueOf(finallnum.getText()))  + "");
                }
            }
        }

    }
}
