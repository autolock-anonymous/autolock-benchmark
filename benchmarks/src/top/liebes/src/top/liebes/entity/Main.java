package top.liebes.entity;

class Main{
    public static void main(String[] args) {
        FileUtil fileUtil = new FileUtil();
        fileUtil.getFile("123");
        fileUtil.handle();
        fileUtil.showFiles();
    }
}