package com.liberty.share.util;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * @author nickChen
 * create on 2017-10-30 10:15.
 */

public class CaptchaUtil {
    // 图片的宽度
    private static final int CAPTCHA_WIDTH = 90;
    // 图片的高度
    private static final int CAPTCHA_HEIGHT = 20;
    // 验证码的个数
    private static final int CAPTCHA_CODECOUNT = 4;

    private static final int XX = 15;
    private static final int CAPTCHA_FONT_HEIGHT = 18;
    private static final int CAPTCHA_CODE_Y = 16;
    private static final char[] codeSequence = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    // 过期时间为60秒
    private static final long EXPIRE_MINUTES = 60;

    /**
     * 初始化画板
     *
     * @return Object[] 第一位是BufferedImage 第二位是由前者生成的Graphics2D
     */
    private static Object[] init() {
        // 定义图像 Buffer
        BufferedImage buffImg = new BufferedImage(CAPTCHA_WIDTH, CAPTCHA_HEIGHT, BufferedImage.TYPE_INT_RGB);
        // 创建一个绘制图像的对象
        Graphics2D g = buffImg.createGraphics();
        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, CAPTCHA_WIDTH, CAPTCHA_HEIGHT);
        // 设置字体
        g.setFont(new Font("Fixedsys", Font.BOLD, CAPTCHA_FONT_HEIGHT));
        // 设置字体边缘光滑
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 画边框
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, CAPTCHA_WIDTH - 1, CAPTCHA_HEIGHT - 1);
        return new Object[]{buffImg, g};
    }

    private static void drawInterferenceLine(Graphics2D g) {
        Random random = new Random();
        // 随机产生20条干扰线，使图象中的认证码不易被其它程序探测到。
        for (int i = 0; i < 20; i++) {
            genRandomColor(g);
            int x = random.nextInt(CAPTCHA_WIDTH);
            int y = random.nextInt(CAPTCHA_HEIGHT);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
    }

    private static void genRandomColor(Graphics2D g) {
        Random random = new Random();
        // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同
        // 用随机产生的颜色将验证码绘制到图像中
        g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
    }

    private static String genBase64StrFromBuffer(BufferedImage buffImg) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        final boolean png = ImageIO.write(buffImg, "png", out);
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(out.toByteArray());
    }

    /**
     * 随机生成4位的验证码
     *
     * @return String[] 第一位是验证码字符串，第二位是验证码图片对应的base64位字符串
     * @throws IOException
     */
    public static String[] genCaptcha() throws IOException {
        Object[] obj = init();
        BufferedImage buffImg = (BufferedImage) obj[0];
        Graphics2D g = (Graphics2D) obj[1];
        drawInterferenceLine(g);
        // 保存随机产生的验证码，以便用户登录后进行验证
        StringBuilder randomCode = new StringBuilder();
        Random random = new Random();
        int red = 0, green = 0, blue = 0;
        // 随机产生验证码
        for (int i = 0; i < CAPTCHA_CODECOUNT; i++) {
            // 得到随机产生的验证码数字
            String code = String.valueOf(codeSequence[random.nextInt(36)]);
            genRandomColor(g);
            g.drawString(code, (i + 1) * XX, CAPTCHA_CODE_Y);
            // 将产生的随机数组合在一起
            randomCode.append(code);
        }
        return new String[]{randomCode.toString(), genBase64StrFromBuffer(buffImg)};
    }

    /**
     * 根据入参生成对应的图片，code长度为 4位
     * @param code
     * @return
     * @throws IOException
     */
    private static String genCaptcha(String code) throws IOException {
        Object[] obj = init();
        BufferedImage buffImg = (BufferedImage) obj[0];
        Graphics2D g = (Graphics2D) obj[1];
        drawInterferenceLine(g);
        for (int i = 0; i < code.length(); i++) {
            genRandomColor(g);
            g.drawString(String.valueOf(code.charAt(i)), (i + 1) * XX, CAPTCHA_CODE_Y);
        }
        return genBase64StrFromBuffer(buffImg);
    }

    public static void main(String[] args) throws IOException {
        for(String string : genCaptcha()) {
            System.out.println(string);
        }

    }
}
