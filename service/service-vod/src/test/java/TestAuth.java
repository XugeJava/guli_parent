import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.xuge.vod.utils.InitVodClient;

/**
 * author: yjx
 * Date :2022/4/2023:08
 **/
public class TestAuth {

  /*获取播放凭证函数*/
  public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client) throws Exception {
    GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
    request.setVideoId("db1bc6bb5bed4e77a662d8209328262a");
    return client.getAcsResponse(request);
  }
  public static void main(String[] args) throws ClientException {
    //根据id获取视屏凭证
    //2.创建初始化对象
    DefaultAcsClient client = InitVodClient.initVodClient("LTAI5tNf4mAyWoNHn5A86CZB", "rBgxvni0xTCjkrvd0zLSud6FbiDpGW");
    GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
    try {
      response = getVideoPlayAuth(client);
      //播放凭证
      System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
      //VideoMeta信息
      System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
    } catch (Exception e) {
      System.out.print("ErrorMessage = " + e.getLocalizedMessage());
    }
    System.out.print("RequestId = " + response.getRequestId() + "\n");
  }

}
