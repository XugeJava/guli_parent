import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.xuge.vod.utils.InitVodClient;

import java.util.List;

/**
 * author: yjx
 * Date :2022/4/2022:48
 **/
public class TestVod {
  public static void main(String[] args) throws ClientException {


    //1.根据视屏id获取凭证

    //2.创建初始化对象
    DefaultAcsClient client = InitVodClient.initVodClient("LTAI5tNf4mAyWoNHn5A86CZB", "rBgxvni0xTCjkrvd0zLSud6FbiDpGW");



    //3.创建获取视屏地址request和response对象
    GetPlayInfoResponse response = new GetPlayInfoResponse();
    GetPlayInfoRequest request = new GetPlayInfoRequest();
    //4.向request对象设置id
    request.setVideoId("c75ee7bc3c2b4c4abb99345f24519874");
    //5.调用初始化对象，传递request,获取数据
    response= client.getAcsResponse(request);
    List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
    //播放地址
    for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
      System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
    }
    //Base信息
    System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");


  }
}
