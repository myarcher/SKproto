package com.mini.skproto.service

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * @author
 * @version 1.0
 * @date 2017/4/19
 */

interface BService {
    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Public&a=login")
    fun login(@Field("app_id") app_id: String, @Field("phone") phone: String, @Field("password") password: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Public&a=sendSMS")
    fun sendCode(@Field("app_id") app_id: String, @Field("phone") phone: String, @Field("type") type: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Public&a=register")
    fun editPass(@Field("app_id") app_id: String, @Field("phone") s: String, @Field("password") password: String, @Field("code") code: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Public&a=forgotPWD")
    fun forgetPass(@Field("app_id") app_id: String, @Field("phone") phone: String, @Field("password") password: String, @Field("code") code: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=index")
    fun getSelfinfo(@Field("app_id") app_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Shop&a=commendGoods")
    fun getSelfList(@Field("app_id") app_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=collect")
    fun getColloect(@Field("app_id") app_id: String, @Field("p") p: Int, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=collectDelete")
    fun deleteColle(@Field("app_id") app_id: String, @Field("id") id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=follow")
    fun getFollowInfo(@Field("app_id") app_id: String, @Field("p") p: Int, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=followDelete")
    fun deleteFollow(@Field("app_id") app_id: String, @Field("id") id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=browser")
    fun getHistoryInfo(@Field("app_id") app_id: String, @Field("p") p: Int, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=browserDelete")
    fun deleteHistory(@Field("app_id") app_id: String, @Field("id") id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    //获取个人信息
    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=userInfo")
    fun getUserInfo(@Field("app_id") app_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=userInfo")
    fun getUserAgreeinfo(@Field("app_id") app_id: String): Call<String>


    //关于我们显示数据
    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=aboutUS")
    fun getAbout(@Field("app_id") app_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=feedback")
    fun sendBackMess(@Field("app_id") app_id: String, @Field("content") content: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=updateUser")
    fun updataUserInfo(@Field("app_id") app_id: String, @Field("avater") avater: String, @Field("nickname") nickname: String, @Field("sex") sex: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    //获取地址列表数据 
    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=addressList")
    fun getAddress(@Field("app_id") app_id: String, @Field("p") p: Int, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    //设置默认地址
    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=setDefaultAddress")
    fun setAddressDefault(@Field("app_id") app_id: String, @Field("id") id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    //新建地址
    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=addressAdd")
    fun addAddress(@Field("app_id") app_id: String, @Field("username") username: String, @Field("phone") phone: String, @Field("province_name") province_name: String, @Field("city_name") city_name: String, @Field("area_name") area_name: String, @Field("detail_address") detail_address: String, @Field("token") tokens: String, @Field("device_id") divice: String): Call<String>

    //更新地址
    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=updateAddress")
    fun updataAddress(@Field("app_id") app_id: String, @Field("id") id: String, @Field("username") username: String, @Field("phone") phone: String, @Field("province_name") province_name: String, @Field("city_name") city_name: String, @Field("area_name") area_name: String, @Field("detail_address") detail_address: String, @Field("token") tokens: String, @Field("device_id") divice: String): Call<String>

    //删除地址
    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=deleteAddress")
    fun deleteAddress(@Field("app_id") app_id: String, @Field("id") id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=orderList")
    fun getOrderlist(@Field("app_id") app_id: String, @Field("p") p: Int, @Field("appraise") appraise: String, @Field("status") status: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    //1邮箱绑定 2验证邮箱绑定手机号
    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=sendEmail")
    fun sendEmail(@Field("app_id") app_id: String, @Field("email") email: String, @Field("type") type: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    //绑定邮箱
    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=fristBandEmail")
    fun sendEmailCode(@Field("app_id") app_id: String, @Field("email") email: String, @Field("code") code: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=checkPhone")
    fun checkPhone(@Field("app_id") app_id: String, @Field("auth_code") auth_code: String, @Field("phone") phone: String, @Field("code") code: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=bindPhone")
    fun bindPhone(@Field("app_id") app_id: String, @Field("auth_code") auth_code: String, @Field("phone") phone: String, @Field("code") code: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=checkEmail")
    fun checkEmil(@Field("app_id") app_id: String, @Field("email") email: String, @Field("code") code: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=bindEmail")
    fun editEmail(@Field("app_id") app_id: String, @Field("phone") phone: String, @Field("code") code: String, @Field("email") email: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=orderCancel")
    fun cancelorder(@Field("app_id") app_id: String, @Field("id") id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=orderInfo")
    fun getOrderifnos(@Field("app_id") app_id: String, @Field("id") id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=orderDelete")
    fun deleteorder(@Field("app_id") app_id: String, @Field("id") id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=receipt")
    fun okorder(@Field("app_id") app_id: String, @Field("id") id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=commentAdd")
    fun sendPingJiaMess(@Field("app_id") app_id: String, @Field("comment_img") comment_img: String, @Field("id") id: String, @Field("content") content: String, @Field("account") account: String, @Field("flow") flow: String, @Field("manner") manner: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=logistics")
    fun getGoodslist(@Field("app_id") app_id: String, @Field("id") id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=payMode")
    fun getPaylist(@Field("app_id") app_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=refundReason")
    fun getApplayInfoList(@Field("app_id") app_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=applyRefund")
    fun sendApplayInfo(@Field("app_id") app_id: String, @Field("id") id: String, @Field("reason") reason: String, @Field("explain") explain: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=logout")
    fun exit(@Field("app_id") app_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=messageType")
    fun getMessList(@Field("app_id") app_id: String, @Field("p") p: Int, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=messageList")
    fun getMessItem(@Field("app_id") app_id: String, @Field("p") p: Int, @Field("type") type: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=customerService")
    fun getAfterTwolist(@Field("app_id") app_id: String, @Field("p") p: Int, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=searchProgress")
    fun getAfterGoodsList(@Field("app_id") app_id: String, @Field("id") id: String, @Field("type") type: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=updateOrderAddress")
    fun updataOrderAddress(@Field("app_id") app_id: String, @Field("id") id: String, @Field("address_id") address_id: String, @Field("token") tokens: String, @Field("device_id") divice: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Shop&a=index")
    fun getMainInfo(@Field("app_id") app_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Cart&a=cartList")
    fun getShopingList(@Field("app_id") app_id: String, @Field("p") p: Int, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=applyService")
    fun sendApplayGoodsInfo(@Field("app_id") app_id: String, @Field("image") image: String, @Field("id") id: String, @Field("status") status: String, @Field("reason_id") reason_id: String, @Field("explain") explain: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=inputLogisticsGoodAttr")
    fun getArrtibute(@Field("app_id") app_id: String, @Field("id") id: String, @Field("status") status: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=inputLogistics")
    fun sendWuliu(@Field("app_id") app_id: String, @Field("id") id: String, @Field("sizeattr_id") sizeattr_id: String, @Field("status") status: String, @Field("return_express") return_express: String, @Field("return_name") return_name: String, @Field("token") tokens: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Cart&a=deleteFromCart")
    fun deleteShop(@Field("app_id") app_id: String, @Field("cart_id") cart_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Category&a=cateleftMenu")
    fun getSortinfo(@Field("app_id") app_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Goods&a=searchHistory")
    fun getSearcherMain(@Field("app_id") app_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Goods&a=search")
    fun getSearcherResult(@Field("app_id") app_id: String, @Field("keyword") keyword: String, @Field("order") order: String, @Field("status") status: String, @Field("category_id") category_id: String, @Field("brand_id") brand_id: String, @Field("p") p: Int, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Cart&a=buyFromCart")
    fun setOrder(@Field("app_id") app_id: String, @Field("cart_id") cart_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Goods&a=goodsInfo")
    fun getComminfo(@Field("app_id") app_id: String, @Field("goods_id") goods_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Goods&a=commentList")
    fun getAllComment(@Field("app_id") app_id: String, @Field("p") p: Int, @Field("goods_id") goods_id: String, @Field("type") type: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Cart&a=updateCartGoods")
    fun editOrder(@Field("app_id") app_id: String, @Field("cart_id") cart_id: String, @Field("sizeattr") sizeattr: String, @Field("num") num: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Cart&a=submitOrder")
    fun sentOrderInfo(@FieldMap map: Map<String, String>): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Store&a=index")
    fun getUserShopOne(@Field("app_id") app_id: String, @Field("store_id") shop_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Store&a=goodsList")
    fun getUserShopTwo(@Field("app_id") app_id: String, @Field("p") p: Int, @Field("store_id") store_id: String, @Field("order") order: String, @Field("status") status: String, @Field("keyword") keyword: String, @Field("category_id") category_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Store&a=newGoods")
    fun getUserShopThree(@Field("app_id") app_id: String, @Field("store_id") shop_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Store&a=follow")
    fun followShop(@Field("app_id") app_id: String, @Field("store_id") shop_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Cart&a=Buy")
    fun commpelePay(@Field("app_id") app_id: String, @Field("goods_id") goods_id: String, @Field("goods_num") goods_num: String, @Field("sizeattr_id") sizeattr_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Cart&a=addToCart")
    fun toShopp(@Field("app_id") app_id: String, @Field("goods_id") goods_id: String, @Field("goods_num") goods_num: String, @Field("sizeattr_id") sizeattr_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Store&a=category")
    fun getHotType(@Field("app_id") app_id: String, @Field("store_id") shop_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Goods&a=addToCollect")
    fun collectComminfo(@Field("app_id") app_id: String, @Field("goods_id") goods_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Goods&a=commentTotal")
    fun commentTotal(@Field("app_id") app_id: String, @Field("goods_id") goods_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Goods&a=zan")
    fun upLove(@Field("app_id") app_id: String, @Field("id") id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Goods&a=goodsContent")
    fun getTextPic(@Field("app_id") app_id: String, @Field("goods_id") goods_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Public&a=OtherLogin")
    fun loginThree(@Field("app_id") app_id: String, @Field("type") type: String, @Field("nickname") nickname: String, @Field("sex") sex: String, @Field("headimg") headimg: String, @Field("openid") openid: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Public&a=OtherLogin")
    fun cancelApply(@Field("app_id") app_id: String, @Field("id") id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @Streaming
    @GET
    fun DownApk(@Url url: String): Call<ResponseBody>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=unreadTotal")
    fun getUnreadCount(@Field("app_id") app_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    //获取版本信息
    @FormUrlEncoded
    @POST("index.php?m=Api5&c=index&a=getStart")
    fun getVersion(@Field("app_id") app_id: String, @Field("current_vcode") current_vcode: String, @Field("status") status: String, @Field("token") token: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Cart&a=goodsTotal")
    fun getShopCount(@Field("app_id") app_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    //@POST("index.php?m=Api5&c=Alipay&a=getoSrderStr")
    @POST("index.php?m=Api5&c=Alipay&a=getOrderStr")
    fun getoSrderStr(@Field("app_id") app_id: String, @Field("order_id") order_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Goods&a=searchFilter")
    fun getSearcherFilter(@Field("app_id") app_id: String, @Field("keyword") keyword: String, @Field("brand_id") brand_id: String, @Field("category_id") category_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=collectDelete")
    fun deletecollectGoods(@Field("app_id") app_id: String, @Field("id") id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=cart&a=upDell")
    fun updataFreightPrice(@Field("app_id") app_id: String, @Field("address_id") address_id: String, @Field("token") tokens: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=region")
    fun getAddressInfo(@Field("app_id") app_id: String, @Field("pid") pid: String, @Field("level") level: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Public&a=registerFirst")
    fun regeisterfirst(@Field("app_id") app_id: String, @Field("phone") phone: String, @Field("code") code: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Public&a=registerSecond")
    fun regeisterTwo(@Field("app_id") app_id: String, @Field("phone") phone: String, @Field("password") password: String, @Field("repassword") repassword: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Category&a=category")
    fun getSortIteminfo(@Field("app_id") app_id: String, @Field("id") id: String, @Field("category_one_id") category_one_id: String, @Field("category_second_id") category_second_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Category&a=category")
    fun getAllSortList(@Field("app_id") app_id: String, @Field("p") p: Int, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=myCard")
    fun getCardCouponsList(@Field("app_id") app_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=user&a=redeemGoodsInfo")
    fun getCardCouponsListForKey(@Field("app_id") app_id: String, @Field("code") code: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=useRedeemCode")
    fun getCardCouponsGoods(@Field("app_id") app_id: String, @Field("id") id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=goods&a=getGoodsList")
    fun getSortsList(@Field("app_id") app_id: String, @Field("field") field: String, @Field("status") status: String, @Field("p") p: String, @Field("contact_id") contact_id: String, @Field("province") province: String, @Field("brand_id") brand_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=index&a=appindex")
    fun getMainThreeInfo(@Field("app_id") app_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=index&a=getAppcate")
    fun getMainInfoList(@Field("app_id") app_id: String, @Field("p") p: Int): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=index&a=appindex")
    fun getMainFourInfo(@Field("app_id") app_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=serviceList")
    fun getNewAfterOneList(@Field("app_id") appId: String, @Field("p") p: Int, @Field("keyword") keyword: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=customerService")
    fun getNewAfterTwoList(@Field("app_id") app_id: String, @Field("keyword") keyword: String, @Field("p") p: Int, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=searchProgress")
    fun getNewCustomerList(@Field("app_id") app_id: String, @Field("order_after_no") order_after_no: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=applyIntervention")
    fun sendNewCustomerInfo(@Field("app_id") app_id: String, @Field("image") image: String, @Field("order_after_no") order_after_no: String, @Field("explain") explain: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=applyCancel")
    fun cancelCustomer(@Field("app_id") app_id: String, @Field("order_after_no") order_after_no: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=changeServiceType")
    fun changeServiceType(@Field("app_id") app_id: String, @Field("image") image: String, @Field("order_after_no") order_after_no: String, @Field("status") status: String, @Field("reason_id") reason_id: String, @Field("explain") explain: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=interventionDetail")
    fun getCustomerList(@Field("app_id") app_id: String, @Field("order_after_no") order_after_no: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=cancelIntervention")
    fun cancelIntervention(@Field("app_id") app_id: String, @Field("id") id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    fun continueCustomer(@Field("app_id") app_id: String, @Field("order_after_no") order_after_no: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=getRefuseCount")
    fun getRefuseCount(@Field("app_id") app_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=getRefuseData")
    fun getRefualInfo(@Field("app_id") app_id: String, @Field("order_after_no") order_after_no: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=writeRefuseReason")
    fun sendRefaulMess(@Field("app_id") app_id: String, @Field("image") image: String, @Field("order_after_no") order_after_no: String, @Field("explain") explain: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=getLogisticsCompany")
    fun getLogisticsCompany(@Field("app_id") app_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=afterLogistics")
    fun getNewGoodsList(@Field("app_id") app_id: String, @Field("order_after_no") order_after_no: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=User&a=serviceListTwo")
    fun getserviceListTwo(@Field("app_id") app_id: String, @Field("control") control: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Voucher&a=voucherList")
    fun getMainCardList(@Field("app_id") appId: String, @Field("p") p: Int, @Field("status") status: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Voucher&a=unGetVoucherList")
    fun getCardReceiveList(@Field("app_id") appId: String, @Field("p") p: Int, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Voucher&a=getVoucher")
    fun getCardinfo(@Field("app_id") appId: String, @Field("id") id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>


    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Voucher&a=orderVoucherList")
    fun getuserCardList(@Field("app_id") appId: String, @Field("p") p: String, @Field("goods_id") goods_id: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=index&a=getAppShopList")
    fun getMainFourlist(@Field("app_id") appId: String, @Field("p") p: String): Call<String>

    @FormUrlEncoded
    @POST("index.php?m=Api5&c=Voucher&a=cancelDialog")
    fun closeDialog(@Field("app_id") appId: String, @Field("is_selected") is_selected: String, @Field("token") token: String, @Field("device_id") device_id: String): Call<String>
}