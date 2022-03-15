import com.example.bithomeassignment.models.MovieList
import com.example.bithomeassignment.models.Trailer
import com.example.bithomeassignment.network.ApiService
import com.example.bithomeassignment.network.Constants
import com.example.bithomeassignment.utils.LoggerUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * Created by Netanel Amar on 08/03/2022.
 * NetanelCA2@gmail.com
 */
//A network manager singleton to manage al network traffic
class NetworkManager(private val apiService: ApiService) {
    val TAG = this::class.simpleName.toString()

    // Suspend fun is added because I'm using coroutines
    suspend fun getAllData(endPoint:String, pageNum: Int): MovieList {
        LoggerUtils.info(TAG,"getAllData")
        return apiService.getAllMovies(endPoint,Constants.API_KEY, pageNum)
    }

    // Suspend fun is added because I'm using coroutines
    suspend fun getTrailer(movieId:String): Trailer {
        LoggerUtils.info(TAG,"getAllData")
        return apiService.getTrailer(movieId,Constants.API_KEY)
    }
}

