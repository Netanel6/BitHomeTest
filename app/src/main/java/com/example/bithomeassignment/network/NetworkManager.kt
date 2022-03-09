import com.example.bithomeassignment.models.MovieList
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
class NetworkManager {
    val TAG = this::class.simpleName.toString()
    private var _apiService: ApiService

    suspend fun getAllData(pageNum: Int): MovieList {
        LoggerUtils.info(TAG,"getAllData")
        return _apiService.getAllMovies(Constants.API_KEY, pageNum)
    }


    suspend fun getByTopRated(pageNum: Int): MovieList {
        LoggerUtils.info(TAG,"getByPopularity")
        return _apiService.getMoviesByTopRated(Constants.API_KEY, pageNum)
    }

    suspend fun getByUpcoming(pageNum: Int): MovieList {
        LoggerUtils.info(TAG,"getByUpcoming")
        return _apiService.getMoviesByUpcoming(Constants.API_KEY, pageNum)
    }

    suspend fun getByNowPlaying(
        startReleaseDate: String?,
        endReleaseDate: String?,
    ): MovieList {
        LoggerUtils.info(TAG,"getByNowPlaying")
        return _apiService.getMoviesByNowPlaying(Constants.API_KEY,
            startReleaseDate!!, endReleaseDate!!)
    }

    class HeaderInterceptor : Interceptor {
        private val TAG = this::class.simpleName.toString()
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val response: okhttp3.Response
            val requestBuilder: Request.Builder = chain.request().newBuilder()
            val request: Request = requestBuilder.build()
            response = chain.proceed(request)
            LoggerUtils.info(TAG, response.toString())
            return response
        }
    }

    init {
        LoggerUtils.info(TAG, "Initializing Retrofit...")
        val client: OkHttpClient =
            OkHttpClient.Builder().addInterceptor(HeaderInterceptor()).build()
        val retrofit: Retrofit =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL).client(client).build()
        _apiService = retrofit.create(ApiService::class.java)
    }

}

