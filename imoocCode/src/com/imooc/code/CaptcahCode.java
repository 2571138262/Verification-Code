package com.imooc.code;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 验证码工具类
 */
public class CaptcahCode {
    /**
     * 验证码的生成方法： Java后端会产生一张图片，通过流的方式传递给浏览器，通过Image标签产生我们的图片，
     *                    因为往浏览器上输出，所以需要一个响应流的对象
     * @param response
     * @return
     */
    public static String drawImage(HttpServletResponse response) {
        // 1、定义一个字符串拼接的StringBuilder
        StringBuilder builder = new StringBuilder();
        // 准备产生4个字符串的随机数
        for (int i = 0; i < 4; i++) {
            builder.append(randomChar());
        }
        String code = builder.toString();

        // 2、定义图片的宽度和高度
        int width = 120;
        int height = 25;

        // 建立BufferedImage对象，制定图片的长度和宽度以及色彩
        // BufferedImage对象，是图片的一个缓冲流，这个图片的缓冲流主要是将绘制对象(java.awt.Graphics2D)在内存操作过程中的每一个步骤都回写入进去
        // 也就是利用这个缓冲流进行输出产生图片， 达到一个高效的目的
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        // 3、获取到Graphics2D绘制对象，开始绘制验证码
        Graphics2D g = bi.createGraphics();

        // 4、设置文字的字体和大小
        Font font = new Font("微软雅黑", Font.PLAIN, 20);
        // 设置字体的颜色
        Color color = new Color(0,0,0);
        // 设置字体
        g.setFont(font);
        // 设置颜色
        g.setColor(color);
        // 设置背景
        g.setBackground(new Color(226, 226, 240));
        // 开始绘制对象
        g.clearRect(0, 0, width, height);
        // 绘制形状， 回去矩形对象
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(code,context);

        // 计算文件的坐标和间距
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = bounds.getY();
        double baseY = y - ascent;
        g.drawString(code, (int)x, (int)baseY);
        //结束绘制
        g.dispose();

        //将生成的图片放到图片缓冲流中
        try {
            ImageIO.write(bi,"jpg", response.getOutputStream());
            // 刷新响应流
            response.flushBuffer();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 算术表达式验证码
     * 1、干扰线的产生
     * 2、范围随机颜色， 随机数
     * @param response
     * @return
     */
    public static String drawImageVerificate(HttpServletResponse response){

        // 定义验证码的宽度和高度
        int width = 100, height = 30;
        // 在内存中创建图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        // 创建图片的一个上下文
        Graphics2D g = image.createGraphics();
        // 产生随机对象 , 次随机对象主要用于算术表达式的数字
        Random random = new Random();
        // 设置背景
        g.setColor(getRandomColor(240, 250));
        // 设置字体
        g.setFont(new Font("微软雅黑", Font.PLAIN, 22));
        // 开始绘制
        g.fillRect(0, 0, width, height);
        // 干扰线的绘制，绘制线条到图片中
        g.setColor(getRandomColor(180, 230));
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(60);
            int y1 = random.nextInt(60);
            g.drawLine(x, y, x1, y1);
        }

        // 开始进行对算术验证码表达式的拼接
        int num1 = (int)(Math.random() * 10 + 1);
        System.out.println(num1);
        int num2 = (int)(Math.random() * 10 + 1);
        System.out.println(num2);
        int fuhao = random.nextInt(3);// 产生一个[0, 2]之间的随机整数
        // 记录符号
        String fuhaostr = null;
        int result = 0;
        switch (fuhao){
            case 0 : fuhaostr = "+"; result = num1 + num2;break;
            case 1 : fuhaostr = "-"; result = num1 - num2;break;
            case 2 : fuhaostr = "*"; result = num1 * num2;break;
        }

        // 拼接算术表达式 ，用于图片显示的格式
        String calc = num1 + " " + fuhaostr + " " + num2 + " = ?";
        // 设置随机颜色
        g.setColor(new Color(20 + random.nextInt(110),20 + random.nextInt(110),20 + random.nextInt(110)));
        // 绘制表达式
        g.drawString(calc, 5, 25);
        // 结束绘制
        g.dispose();
        try{
            // 输出图片到页面
            ImageIO.write(image,"JPEG", response.getOutputStream());
            return String.valueOf(result);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 范围随机颜色
     * @param fc
     * @param bc
     * @return
     */
    public static Color getRandomColor(int fc, int bc) {
        // 利用随机数
        Random random = new Random();
        // 随机颜色 ， 了解颜色 - Color(red, green, blue).rgb三元色 0-255
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 此方法用于产生随机数字母和数字
     * @return
     */
    private static char randomChar() {
        // 1、定义验证码需要的字母和数字
        String string = "QWERTYUIOPASDFGHJKLZXCVBNM0123456789";
        // 2、定义随机对象
        Random random = new Random();
        return string.charAt(random.nextInt(string.length() - 1));
    }
}
