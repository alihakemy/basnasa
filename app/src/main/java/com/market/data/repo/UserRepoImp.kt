package com.market.data.repo

import android.util.Log
import com.market.BuildConfig
import com.market.data.models.get.addComment.DefaultResponse
import com.market.data.models.get.currency.Currency
import com.market.data.models.get.fav.Favourites
import com.market.data.models.get.homeusers.HomeUser
import com.market.data.models.get.links.SocialLinks
import com.market.data.models.get.login.LoginResponse
import com.market.data.models.get.offers.Offers
import com.market.data.models.get.paymentPackages.PaymentPackages
import com.market.data.models.get.popups.popups
import com.market.data.models.get.productdetails.ProductDetails
import com.market.data.models.get.search.SearchResults
import com.market.data.models.get.setions.Sections
import com.market.data.models.get.tagerdetails.TagerDetails
import com.market.data.models.get.tagerprofile.TagerProfile
import com.market.data.models.get.terms.TermsCondtionModel
import com.market.data.services.apis
import com.market.utils.ResultState
import com.market.utils.toRequestBody
import okhttp3.MultipartBody
import javax.inject.Inject

class UserRepoImp @Inject constructor(private val userService: apis) {


    suspend fun getCurrency():ResultState<Currency> {
        return try {


            ResultState.Success(userService.getCurrency())


        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage.toString())
        }
    }


    suspend fun updateUserProfile(hashMap: HashMap<String, String>): ResultState<LoginResponse> {

        return try {


            ResultState.Success(userService.updateUserProfile(hashMap))


        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage.toString())
        }

    }


    private fun getToken(token: String): String = "Bearer $token"


    suspend fun search(query: String, lat: String, long: String): ResultState<SearchResults> {

        return try {
            val result = userService.getSearch(query, lat, long)
            ResultState.Success(result)
        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage.toString())

        }

    }

    suspend fun deleteComments(ProductId: String, token: String): ResultState<DefaultResponse> {

        return try {
            val result = userService.deleteComment(getToken(token), ProductId)
            ResultState.Success(result)
        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage.toString())

        }

    }

    suspend fun editeComments(
        hashMap: HashMap<String, String>,
        token: String,
        commentId: Int
    ): ResultState<DefaultResponse> {

        return try {
            val result = userService.editComment(getToken(token), hashMap, commentId)
            ResultState.Success(result)
        } catch (e: Exception) {

            Log.e("ResultsEROR", e.localizedMessage.toString())
            ResultState.Error(e.localizedMessage.toString())

        }

    }

    suspend fun addComments(
        hashMap: HashMap<String, String>,
        token: String
    ): ResultState<DefaultResponse> {

        return try {
            val result = userService.addComment(getToken(token), hashMap)
            ResultState.Success(result)
        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage.toString())

        }

    }

    suspend fun productDetails(
        productId: String, latitude: String,
        longitude: String
    ): ResultState<ProductDetails> {

        return try {
            val result = userService.getProductDetails(productId, latitude, longitude)
            ResultState.Success(result)
        } catch (e: Exception) {
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

    suspend fun getFav(token: String, lat: String, long: String): ResultState<Favourites> {

        return try {
            Log.e("Calledssss", token)

            val result = userService.getFavourites(getToken(token), lat, long)
            Log.e("Called", "Calleds$result")
            ResultState.Success(result)
        } catch (e: Exception) {
            Log.e("Called", "Calleds${e.localizedMessage}")


            ResultState.Error(e.localizedMessage.toString())

        }

    }


    suspend fun getFav(
        token: String,
        lat: String,
        long: String,
        id: String
    ): ResultState<Favourites> {

        return try {
            Log.e("Calledssss", token)

            val result = userService.getFavourites(getToken(token), lat, long, id)
            Log.e("Called", "Calleds$result")
            ResultState.Success(result)
        } catch (e: Exception) {
            Log.e("Called", "Calleds${e.localizedMessage}")


            ResultState.Error(e.localizedMessage.toString())

        }

    }


    suspend fun getOffers(latitude: String, longitude: String): ResultState<Offers> {
        return try {


            val result = userService.getOffer(latitude = latitude, longitude = longitude)
            Log.e("Called", "Calleds$result")
            ResultState.Success(result)


        } catch (e: Exception) {
            Log.e("Called", "Calleds${e.localizedMessage}")


            ResultState.Error(e.localizedMessage.toString())

        }
    }


    suspend fun getOffers(latitude: String, longitude: String, id: String): ResultState<Offers> {
        return try {


            val result = userService.getOffer(
                latitude = latitude, longitude = longitude,
                id.toString()
            )
            Log.e("Called", "Calleds$result")
            ResultState.Success(result)


        } catch (e: Exception) {
            Log.e("Called", "Calleds${e.localizedMessage}")


            ResultState.Error(e.localizedMessage.toString())

        }
    }


    suspend fun getSectionCategories(
        categoriesId: String,
        token: String,
        latitude: String,
        longitude: String
    ): ResultState<Sections> {
        return try {

            val result = userService.getSectionCategories(
                token = getToken(token),
                categoriesId = categoriesId, latitude = latitude, longitude = longitude
            )
            Log.e("Called", "Calleds$result")
            ResultState.Success(result)
        } catch (e: Exception) {
            Log.e("Called", "Calleds${e.localizedMessage}")


            ResultState.Error(e.localizedMessage.toString())

        }
    }

    suspend fun getSectionSubCategories(
        categoriesId: String,
        subCategoriesId: String,
        token: String,
        latitude: String,
        longitude: String
    ): ResultState<Sections> {
        return try {

            val result = userService.getSectionSubCategories(
                token = getToken(token),
                subcategoriesId = subCategoriesId,
                categoriesId = categoriesId, latitude = latitude, longitude = longitude
            )
            Log.e("Called", "Calleds$result")
            ResultState.Success(result)
        } catch (e: Exception) {
            Log.e("Called", "Calleds${e.localizedMessage}")


            ResultState.Error(e.localizedMessage.toString())

        }
    }


    suspend fun addFav(merchant_id: String) {
        try {
            val map: HashMap<String, String> = HashMap()
            map["merchant_id"] = merchant_id

            userService.addFav(map)
        } catch (e: Exception) {

        }


    }

    suspend fun removeFav(merchant_id: String) {
        try {
            userService.deleteFav(merchant_id)
        } catch (e: Exception) {

        }
    }


    suspend fun TagerDetails(
        tagerId: String, latitude: String,
        longitude: String
    ): ResultState<TagerDetails> {

        return try {
            val result = userService.getTagerDetails(tagerId, latitude, longitude)
            ResultState.Success(result)
        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage.toString())

        }

    }

    suspend fun TagerDetails(
        catId: String, tagerId: String, latitude: String,
        longitude: String
    ): ResultState<TagerDetails> {

        return try {
            val result = userService.getTagerDetails(tagerId, catId, latitude, longitude)
            ResultState.Success(result)
        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage.toString())

        }

    }

    suspend fun getPaymentPackages(
        latitude: String,
        longitude: String
    ): ResultState<PaymentPackages> {

        return try {
            val result = userService.getPaymentPackages(latitude, longitude)
            ResultState.Success(result)
        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage.toString())

        }

    }


    suspend fun getTagerProfiles(
        latitude: String,
        longitude: String
    ): ResultState<TagerProfile> {

        return try {
            val result = userService.getTagerProfile(latitude, longitude)
            ResultState.Success(result)
        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage.toString())

        }

    }


    suspend fun getBuyPackage(
        packageId: String,
        onInvoiceCreated: String
    ): ResultState<DefaultResponse> {

        return try {
            val map = HashMap<String, String>()
            map.put("package_id", packageId)
            map.put("invoiceId", onInvoiceCreated)

            val result = userService.getBuyPackage(map)
            ResultState.Success(result)
        } catch (e: Exception) {
            Log.e("ErroePayment", e.localizedMessage.toString())
            ResultState.Error(e.localizedMessage.toString())

        }

    }


    suspend fun addProduct(
        list: ArrayList<MultipartBody.Part>, image: MultipartBody.Part,
        category_id: String,
        mainprice: String,
        discount: String,
        stoke: String,
        name: String,
        currecny: String,
        about: String
    ): ResultState<DefaultResponse> {
        return try {
            ResultState.Success(
                userService.addProduct(
                    category_id = category_id.toRequestBody(),
                    mainprice = mainprice.toRequestBody(),
                    discount = discount.toRequestBody(),
                    stoke = stoke.toRequestBody(),
                    image,
                    name = name.toRequestBody(),
                    list,
                    currecny.toRequestBody(),
                    about.toRequestBody()
                )
            )
        } catch (e: Exception) {
            Log.e("ErroePayment", e.localizedMessage.toString())
            ResultState.Error(e.localizedMessage.toString())

        }

    }


    suspend fun editProduct(
        productId: String,
        list: ArrayList<MultipartBody.Part>, image: MultipartBody.Part,
        category_id: String,
        mainprice: String,
        discount: String,
        stoke: String,
        name: String,
        currecny: String,
        about: String
    ): ResultState<DefaultResponse> {
        return try {
            ResultState.Success(
                userService.editProduct(
                    productId,
                    category_id = category_id.toRequestBody(),
                    mainprice = mainprice.toRequestBody(),
                    discount = discount.toRequestBody(),
                    stoke = stoke.toRequestBody(),
                    image,
                    name = name.toRequestBody(),
                    list,
                    currecny.toRequestBody(),
                    about.toRequestBody(),
                    "put".toRequestBody()
                )
            )
        } catch (e: Exception) {
            Log.e("ErroePayment", e.localizedMessage.toString())
            ResultState.Error(e.localizedMessage.toString())

        }

    }


    suspend fun editProduct(
        productId: String,
        list: ArrayList<MultipartBody.Part>,
        category_id: String,
        mainprice: String,
        discount: String,
        stoke: String,
        name: String,
        currecny: String,
        about: String
    ): ResultState<DefaultResponse> {
        return try {
            ResultState.Success(
                userService.editProduct(
                    productId,
                    category_id = category_id.toRequestBody(),
                    mainprice = mainprice.toRequestBody(),
                    discount = discount.toRequestBody(),
                    stoke = stoke.toRequestBody(),

                    name = name.toRequestBody(),
                    list,
                    currecny.toRequestBody(),
                    about.toRequestBody(),
                    "put".toRequestBody()
                )
            )
        } catch (e: Exception) {
            Log.e("ErroePayment", e.localizedMessage.toString())
            ResultState.Error(e.localizedMessage.toString())

        }

    }


    suspend fun removeProduct(
        productId: String
    ): ResultState<DefaultResponse> {

        return try {
            val result = userService.removeProduct(productId)
            ResultState.Success(result)
        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage.toString())

        }

    }

    suspend fun getLinksSocial(

    ): ResultState<SocialLinks> {

        return try {
            val result = userService.getLinks()
            ResultState.Success(result)
        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage.toString())

        }

    }
    suspend fun getpopups(

    ): ResultState<popups> {

        return try {
            val result = userService.getPopups()
            ResultState.Success(result)
        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage.toString())

        }

    }

    suspend fun getTerms(
        type: String

    ): ResultState<TermsCondtionModel> {

        return try {
            val result = userService.getPage(type)
            ResultState.Success(result)
        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage.toString())

        }

    }


}