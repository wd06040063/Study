package com.Wang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    /** print name*/
    private final String[] KEYS = { "7","8","9","/","sqrt","4","5","6","*","%","1","2","3","-","1/x","0","+/-",".","+","="};
   /** function name*/
    private final String[] COMMAND = {"Backspace","CE","C"};
    /** M show name*/
    private final String [] M = {" ","MC","MR","MS","M+"};
    /**  button*/
    private JButton keys[] = new JButton[KEYS.length];
    private JButton commands[] = new JButton[COMMAND.length];
    private JButton m[] = new JButton[M.length];
    /**  计算结果的文本框*/
    private JTextField resultText = new JTextField("0") ;

    //标志用户按的是否是整个表达式的第一个数字，或者是运算符后的第一个数字
    private boolean firstDigit  = true;
    //计算的中间结果
    private double resultNum = 0.0;
    //当前的运算符
    private String operator = "=";
    //操作是否合法
    private boolean operateValidFlag = true;

    /**
     * 构造函数
     */
    public Calculator() {
        super();
        //初始化计算器
        init();
        //设置计算器的背景颜色
        this.setBackground(Color.LIGHT_GRAY);
        this.setTitle("计算器");
        //在屏幕（500,300）坐标位置显示计算器
        this.setLocation(500,300);
        //不允许用户修改大小
        this.setResizable(false);
        //使计算器中各组件大小合适
        this.pack();

    }

    /**
     * 初始化计算器
     */
    private void init() {
        //文本右对齐
        resultText.setHorizontalAlignment(JTextField.RIGHT);
        //不允许修改结果文本框
        resultText.setEditable(false);
        //设置文本框背景为白色
        resultText.setBackground(Color.WHITE);
//        resultText.getBorder();
        //初始化计算器上键的按钮，将键放在一个画板内
        JPanel calckeysPanel = new JPanel();
        //用网格布局器，4行5列。水平垂直方向3个像素
        calckeysPanel.setLayout(new GridLayout(4,5,3,3));
        for(int i = 0; i < KEYS.length;i++){
            keys[i] = new JButton(KEYS[i]);
            calckeysPanel.add(keys[i]);
            keys[i].setForeground(Color.blue);
        }
        //运算符键用红色表示，其他键使用蓝色
        keys[3].setForeground(Color.red);
        keys[8].setForeground(Color.red);
        keys[13].setForeground(Color.red);
        keys[18].setForeground(Color.red);
        keys[19].setForeground(Color.red);
        //初始化功能键，都用红色表示，将功能键放在一个画板内
        JPanel commandsPanel = new JPanel();
        //网格布局器1行3列，像素3
        commandsPanel.setLayout(new GridLayout(1,3,3,3));
        for(int i = 0; i<COMMAND.length;i++)
        {
            commands[i] = new JButton(COMMAND[i]);
            commandsPanel.add(commands[i]);
            commands[i].setForeground(Color.red);
        }
        //初始化M键，红色标示，将M键放到一个画板内
        JPanel calmsPanel = new JPanel();
        calmsPanel.setLayout(new GridLayout(5,1,3,3));
        for (int i = 0; i < M.length; i++) {
            m[i] = new JButton(M[i]);
            calmsPanel.add(m[i]);
            m[i].setForeground(Color.red);
        }

        //计算机整体布局，将calckeys和command画板放在计算机中部

        JPanel panell  = new JPanel();
        //画板采用边界布局管理器，间隔3像素
        panell.setLayout(new BorderLayout(3,3));
        panell.add("North",commandsPanel);
        panell.add("Center",calckeysPanel);
    //建立一个画板放文本框
        JPanel top = new JPanel();
        top.setLayout((new BorderLayout()));
        top.add("Center",resultText);

        //整体布局
        getContentPane().setLayout(new BorderLayout(3,5));
        getContentPane().add("North",top);
        getContentPane().add("Center",panell);
        getContentPane().add("West",calmsPanel);
        //为各按钮添加事件侦听器
        //都使用同一个事件侦听器，即本对象。本类的声明中有implements ActionListener
        for (int i = 0; i < KEYS.length; i++) {
            keys[i].addActionListener(this);
        }
        for (int i = 0; i < COMMAND.length; i++) {
            commands[i].addActionListener(this);
        }
        for (int i = 0; i < M.length; i++) {
            m[i].addActionListener(this);
        }

    }
    /*处理事件*/
    public void actionPerformed(ActionEvent e) {
    //获取事件源的标签
        String label = e.getActionCommand();
        if(label.equals(COMMAND[0])){
        //用户按了”Backspace“;
        handleBackspace();
    }else if (label.equals(COMMAND[1])) {
        // 用户按了"CE"键
        resultText.setText("0");
    } else if (label.equals(COMMAND[2])) {
        // 用户按了"C"键
        handleC();
    } else if ("0123456789.".indexOf(label) >= 0) {
        // 用户按了数字键或者小数点键
        handleNumber(label);
        // handlezero(zero);
    } else {
        // 用户按了运算符键
        handleOperator(label);
    }

}
/*处理运算符键被按下的事件*/
    @SuppressWarnings("SingleStatementInBlock")
    private void handleOperator(String key) {
        if (operator.equals("/")) {
        if (getNumberFromText() == 0.0) {
            operateValidFlag = false;
            resultText.setText("Error!!!");
        }else {
            resultNum /= getNumberFromText();
        }
        }else if (operator.equals("1/x")) {
            // 倒数运算
            if (resultNum == 0.0) {
                // 操作不合法
                operateValidFlag = false;
                resultText.setText("零没有倒数");
            } else {
                resultNum = 1 / resultNum;
            }
        } else if (operator.equals("+")) {
            // 加法运算
            resultNum += getNumberFromText();
        } else if (operator.equals("-")) {
            // 减法运算
            resultNum -= getNumberFromText();
        } else if (operator.equals("*")) {
            // 乘法运算
            resultNum *= getNumberFromText();
        } else if (operator.equals("sqrt")) {
            // 平方根运算
            resultNum = Math.sqrt(resultNum);
        } else if (operator.equals("%")) {
            // 百分号运算，除以100
            resultNum = resultNum / 100;
        } else if (operator.equals("+/-")) {
            // 正数负数运算
            resultNum = resultNum * (-1);
        } else if (operator.equals("=")) {
            // 赋值运算
            resultNum = getNumberFromText();
        }
        if (operateValidFlag) {
            // 双精度浮点数的运算
            long t1;
            double t2;
            t1 = (long) resultNum;
            t2 = resultNum - t1;
            if (t2 == 0) {
                resultText.setText(String.valueOf(t1));
            } else {
                resultText.setText(String.valueOf(resultNum));
            }
        }
        // 运算符等于用户按的按钮
        operator = key;
        firstDigit = true;
        operateValidFlag = true;
    }

    /**
     * 从结果文本框中获取数字
     *
     * @return
     */
    private double getNumberFromText() {
        double result = 0;
        try {
            result = Double.valueOf(resultText.getText()).doubleValue();
        } catch (NumberFormatException e) {
        }
        return result;
    }



/*处理数字键被按下的事件*/
private void handleNumber(String key) {
    if (firstDigit) {
        // 输入的第一个数字
        resultText.setText(key);
    } else if ((key.equals(".")) && (resultText.getText().indexOf(".") < 0)) {
        // 输入的是小数点，并且之前没有小数点，则将小数点附在结果文本框的后面
        resultText.setText(resultText.getText() + ".");
    } else if (!key.equals(".")) {
        // 如果输入的不是小数点，则将数字附在结果文本框的后面
        resultText.setText(resultText.getText() + key);
    }
    // 以后输入的肯定不是第一个数字了
    firstDigit = false;
}

    /**
     * 处理C键被按下的事件
     */
    private void handleC() {
        // 初始化计算器的各种值
        resultText.setText("0");
        firstDigit = true;
        operator = "=";
    }




    private void handleBackspace() {
        String text = resultText.getText();
        int i = text.length();
        if(i>0){
            //退格，将文本最后一个字符去掉
            text = text.substring(0,i-1);
            if(text.length()==0){
                //如果文本没有了内容，则初始化计算器的各种值
                resultText.setText("0");
                firstDigit = true;
                operator = "=";
            }else{
                //显示新的文本
                resultText.setText(text);
            }
        }
    }
    public static void main(String args[]) {
        Calculator calculator1 = new Calculator();
        calculator1.setVisible(true);
        calculator1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
