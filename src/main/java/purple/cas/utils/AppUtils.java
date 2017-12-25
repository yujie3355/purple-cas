package purple.cas.utils;

public class AppUtils {
    public static String getPageRootPath(int depth){
        StringBuilder pathBuilder=new StringBuilder();
        pathBuilder.append("./");
        for(int i=1;i<depth;i++){
            pathBuilder.append("../");
        }
        String reslt=pathBuilder.toString();
        return reslt.substring(0,reslt.length()-1);

    }

}
