package com.market.data.repo

import android.util.Log
import com.market.BuildConfig
import com.market.data.models.get.addComment.DefaultResponse
import com.market.data.models.get.fav.Favourites
import com.market.data.models.get.homeusers.HomeUser
import com.market.data.models.get.productdetails.ProductDetails
import com.market.data.models.get.search.SearchResults
import com.market.data.models.get.setions.Sections
import com.market.data.services.User
import com.market.utils.ResultState
import javax.inject.Inject

class UserRepoImp @Inject constructor(private val userService: User) {

    private fun getToken(token: String): String = "Bearer $token"



    suspend fun search(query: String):ResultState<SearchResults>{

        return try {
            val result = userService.getSearch(query)
            ResultState.Success(result)
        }
        catch (e:Exception){
            ResultState.Error(e.localizedMessage.toString())

        }

    }

    suspend fun deleteComments(ProductId:String,token:String):ResultState<DefaultResponse>{

        return try {
            val result = userService.deleteComment(getToken(token),ProductId)
            ResultState.Success(result)
        }
        catch (e:Exception){
            ResultState.Error(e.localizedMessage.toString())

        }

    }

    suspend fun editeComments(hashMap: HashMap<String, String>, token: String, commentId: Int):ResultState<DefaultResponse>{

        return try {
            val result = userService.editComment(getToken(token),hashMap,commentId)
            ResultState.Success(result)
        }
        catch (e:Exception){

            Log.e("ResultsEROR",e.localizedMessage.toString())
            ResultState.Error(e.localizedMessage.toString())

        }

    }
    suspend fun addComments(hashMap: HashMap<String,String>,token:String):ResultState<DefaultResponse>{

        return try {
            val result = userService.addComment(getToken(token),hashMap)
            ResultState.Success(result)
        }
        catch (e:Exception){
            ResultState.Error(e.localizedMessage.toString())

        }

    }
    suspend fun productDetails(productId: String, latitude: String,
                               longitude: String):ResultState<ProductDetails>{

        return try {
            val result = userService.getProductDetails(productId,latitude, longitude)
            ResultState.Success(result)
        }
        catch (e:Exception){
            ResultState.Error(e.localizedMessage.toString())

        }

    }



    suspend fun getUserHomeScreen(
        latitude: String,
        longitude: String
    ): ResultState<HomeUser> {
        return try {

            if (BuildConfig.DEBUG) {
                val result = userService.getUserHomeScreen("29", "48")
                ResultState.Success(result)
            } else {
                val result = userService.getUserHomeScreen(latitude, longitude)
                ResultState.Success(result)
            }

        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage.toString())

        }


    }

    suspend fun getFav(token: String): ResultState<Favourites> {

        return try {
            Log.e("Calledssss", token)

            val result = userService.getFavourites(getToken(token))
            Log.e("Called", "Calleds$result")
            ResultState.Success(result)
        } catch (e: Exception) {
            Log.e("Called", "Calleds${e.localizedMessage}")


            ResultState.Error(e.localizedMessage.toString())

        }

    }

    suspend fun getSectionCategories( categoriesId: String,
                                    token: String,
                                    latitude: String,
                                      longitude: String): ResultState<Sections>{
        return try {

            val result = userService.getSectionCategories(token=getToken(token),
            categoriesId = categoriesId, latitude = latitude, longitude = longitude)
            Log.e("Called", "Calleds$result")
            ResultState.Success(result)
        } catch (e: Exception) {
            Log.e("Called", "Calleds${e.localizedMessage}")


            ResultState.Error(e.localizedMessage.toString())

        }
    }
    suspend fun getSectionSubCategories( categoriesId: String,
                                         subCategoriesId:String,
                                      token: String,
                                      latitude: String,
                                      longitude: String): ResultState<Sections>{
        return try {

            val result = userService.getSectionSubCategories(token=getToken(token),
                subcategoriesId = subCategoriesId,
                categoriesId = categoriesId, latitude = latitude, longitude = longitude)
            Log.e("Called", "Calleds$result")
            ResultState.Success(result)
        } catch (e: Exception) {
            Log.e("Called", "Calleds${e.localizedMessage}")


            ResultState.Error(e.localizedMessage.toString())

        }
    }

}