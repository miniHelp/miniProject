/**
 * Created by Administrator on 2017/10/13 0013.
 */

var zMainUrl,zRegister,zLogin,zMerchantAuth,zMerchantId,zAccountId,zAccountHistory,
zMerchantRateNew,zMerchantRateId,zMerchantKeyNew,zMerchantKeyQuery,zMerchantWhitelist,
zMerchantWhitelistQuery,zMerchantWhitelistDel,zMerchantPaymentorder,zMerchantWithdraworder,zPlatformPaymentorder,
zPlatformWithdraworder,zMerchantSumamountQuery,zPlatformSumamountQuery,zTransactionMerorderQuery,zProfitSumamount,zProfitQuery,
zPayChannelQueryAll,zPayChannelNew1,zPayChannelDel1,zPayChannelCode,zMerchantlimitAdd,zMerchantlimitQuery,zMerchantblacklistAdd,
zMerchantblacklistDel,zMerchantblacklistQuery,zPayChannelAll,zPayChannelNew2,zPayChannelDel2,zPayRealchannelNew,zPayRealchannelDel,
zPayRealchannelAll,zPayRouteQuery,zPayRouteNew,zPayRouteDel,zMerchantParentId,zMerchantlImit,zMerchantRateMerchantid,zMerchantParentAll,
zMerchantChannelQuery,zMerchantChannelUpdate,zUpdatepass,zPayWithdraw,zPlatformTotalcntsQuery,zMerchantTotalcntsQuery,
zPayWithdrawAuth,zSettlementobjNew,zSettlementobjQuery,zSettlementobjDel,zMerchantWhitelistPlatformQuery,
zplatformRealchannelAll,zplatformChannellAll,zChannelQueryall,zAgentProfitHistory,zUpdatesettleperoid,
zMerchantSendcode,zUpdatepaypass,zValidPaypass,zStatisLastest

zMainUrl = "http://106.15.159.189" 
// zMainUrl = "http://106.15.186.0"  
// 01

// zMainUrl = "http://47.75.145.199" 
// 02

/** 商家注册(POST) */
zRegister = zMainUrl + "/smartmerchant/merchant/new"   //已完成
/** 商家登录(POST) */
zLogin = zMainUrl + "/smartmerchant/merchant/login"  //已完成

/** 查询商户信息(POST) merchant_id */
zMerchantId =zMainUrl + "/smartmerchant/merchant/id"   //已完成
/** 查询账户信息(POST) merchant_id */
zAccountId = zMainUrl +"/smartmerchant/merchant/account/id" //已完成

/** 商家的账户历史(POST) */
zAccountHistory = zMainUrl +"/smartmerchant/merchant/account/history"   //已完成


/** 给外方的商户生成或者重置MD5密钥（POST）*/    
zMerchantKeyNew = zMainUrl +"/smartmerchant/merchant/key/new"    //已完成

/** 查询商户的密钥（POST） */
zMerchantKeyQuery = zMainUrl +"/smartmerchant/merchant/key/query"   //已完成


/** 平台给管理员或管理员给商户设置渠道费率(POST) */
zMerchantRateNew = zMainUrl +"/smartmerchant/merchant/rate/new"  




/** 查询商户或管理员的费率（POST) */
zMerchantRateId = zMainUrl +"/smartmerchant/merchant/rate/id"      



/** 新增商家通道交易限额*/
zMerchantlimitAdd= zMainUrl +"/smartrisk/merchantlimit/add"    //已完成
/** 查询商家通道限额设置*/
zMerchantlimitQuery= zMainUrl +"/smartrisk/merchantlimit/query"   //已完成

/** 商家验证通过(POST) */
zMerchantAuth = zMainUrl +"/smartmerchant/merchant/auth"      //待完成。。管理员下级


/** 查询商户所有可以使用的外放通道（POST）*/
zPayChannelAll= zMainUrl +"/smartpayment/pay/channel/all"  
/** 新增商户外放通道*/
zPayChannelNew2= zMainUrl +"/smartpayment/pay/channel/new"   
/** 删除商户外放通道*/
zPayChannelDel2=zMainUrl + "/smartpayment/pay/channel/del"   


/**查询管理员下级商户列表 */
zMerchantParentId= zMainUrl +"/smartmerchant/merchant/parent/id"  //待完成

/**查询总平台下级商户列表 */
zMerchantParentAll= zMainUrl +"/smartmerchant/merchant/platform/all"  //待完成


/**查询商家所开通的通道*/
zMerchantlImit=zMainUrl + "/smartrisk/merchantlimit/query/merchantid"   //待完成


/** 给外放的商户服务器添加IP白名单（POST） */
zMerchantWhitelist = zMainUrl +"/smartmerchant/merchant/whitelist" // 已完成
/** 查询外放商户服务器的白名单（POST） */
zMerchantWhitelistQuery= zMainUrl +"/smartmerchant/merchant/whitelist/query"  //已完成
/** 查询平台服务器的白名单（POST） */
zMerchantWhitelistPlatformQuery= zMainUrl +"/smartmerchant/merchant/whitelist/platform/query"  //已完成
									
/** 移除商家的白名单 (post) */
zMerchantWhitelistDel= zMainUrl +"/smartmerchant/merchant/whitelist/del" //已完成


/** 商家收单订单查询（POST） */
zMerchantPaymentorder= zMainUrl +"/smartclearing/merchant/paymentorder/history" //已完成
							
/** 商家提现订单查询  */
zMerchantWithdraworder= zMainUrl + "/smartclearing/merchant/withdraworder/history" //已完成


/** 管理员/总平台收单查询  */
zPlatformPaymentorder= zMainUrl +"/smartclearing/platform/paymentorder/history" //已完成  


/** 管理员/平台提现订单查询  */
zPlatformWithdraworder= zMainUrl +"/smartclearing/platform/withdraworder/history" //已完成


/** 管理员/平台分润明细查询*/
zProfitQuery=zMainUrl + "/smartclearing/profit/history" // 已完成


/** 商户交易量统计 */
zMerchantSumamountQuery= zMainUrl +"/smartclearing/merchant/sumamount/query" //部分
/**统计商户成交的总交易笔数  */
zMerchantTotalcntsQuery= zMainUrl +"/smartclearing/merchant/totalcnts/query"  //待完成



/** 管理员/平台的交易量统计 */
zPlatformSumamountQuery= zMainUrl +"/smartclearing/platform/sumamount/query"//部分


/** 管理员/平台分润统计*/ 
zProfitSumamount=zMainUrl + "/smartclearing/profit/sumamount"   //部分

 /** 总平台交易笔数统计 */ 
zPlatformTotalcntsQuery=zMainUrl + "/smartclearing/platform/totalcnts/query"  
/** 总平台交易量统计 */ 
zPlatformTotalamountQuery=zMainUrl + "/smartclearing/platform/totalamount/query"   




/** 根据平台订单号查询订单明细 */
zTransactionOrderQuery= zMainUrl +"/smartclearing/transaction/order/query" //已完成
/** 根据商家订单号查询订单明细 */
zTransactionMerorderQuery= zMainUrl +"/smartclearing/transaction/merorder/query" ///已完成
/** 根据渠道的交易订单号查询订单明细 */
zTransactionOutorderQuery=zMainUrl +"/smartclearing/transaction/outorder/query"  //已完成





/** 新增商家的交易黑名单*/
zMerchantblacklistAdd= zMainUrl +"/smartrisk/merchantblacklist/add" //已完成
/** 移除商家黑名单规则*/
zMerchantblacklistDel=zMainUrl + "/smartrisk/merchantblacklist/del" //已完成
/** 查询所有的黑名单*/
zMerchantblacklistQuery= zMainUrl +"/smartrisk/merchantblacklist/query" //已完成


/** 新增/更新实际支付的渠道*/ 
zPayRealchannelNew=zMainUrl + "/smartpayment/pay/realchannel/new" //已完成
/** 删除实际支付的渠道*/
zPayRealchannelDel= zMainUrl +"/smartpayment/pay/realchannel/del" //已完成


/** 查询所有实际支付的通道*/
zPayRealchannelAll=zMainUrl + "/smartpayment/pay/realchannel/all" //已完成



/** 查询所有商户的支付通道的路由*/
zPayRouteQuery=zMainUrl + "/smartpayment/pay/route/query"  //已完成
/** 新增/更新商户的外放通道到实际支付渠道的路由*/

zPayRouteNew=zMainUrl + "/smartpayment/pay/route/new"  //已完成


/** 删除商户的外方渠道到支付渠道的路由*/
zPayRouteDel=zMainUrl + "/smartpayment/pay/route/del"  //已完成   


/** 查询商家所有的外放通道*/
zMerchantChannelQuery=zMainUrl + "/smartmerchant/merchant/channel/query"  
/** 关闭或者打开商家的通道*/
zMerchantChannelUpdate=zMainUrl + "/smartmerchant/merchant/channel/update"   
/**  给商家配置支付通道*/
zMerchantChannelNew=zMainUrl + "/smartmerchant/merchant/channel/new"   


/** 商家外方所有渠道查询*/
zPayChannelQueryAll= zMainUrl +"/smartpayment/pay/channel/all" //  已完成
/** 商家外方渠道添加/更新*/
zPayChannelNew1=zMainUrl + "/smartpayment/pay/channel/new"   //已完成 
/** 商家外放渠道删除*/
zPayChannelDel1= zMainUrl +"/smartpayment/pay/channel/del" //已完成
/** 商家外方按code渠道查询*/
zPayChannelCode= zMainUrl +"/smartpayment/pay/channel/code"  //已完成


/** 更新商户登录密码*/
zUpdatepass= zMainUrl +"/smartmerchant/merchant/updatepass" 

/** 商户发起提现*/
zPayWithdraw= zMainUrl +"/smartpayment/pay/withdraw" 

/** 通过或拒绝待处理提现订单*/
zPayWithdrawAuth= zMainUrl +"/smartpayment/pay/withdraw/auth" 

/** 查询商户或管理员费率列表(POST) */
zMerchantRateMerchantid = zMainUrl +"/smartmerchant/merchant/rate/merchantid"  



/** 新增结算商资料(POST) */
zSettlementobjNew = zMainUrl +"/smartpayment/settlementobj/new"  
/** 查询所有结算商户(POST) */
zSettlementobjQuery = zMainUrl +"/smartpayment/settlementobj/query"  
/** 删除结算商户(POST) */
zSettlementobjDel = zMainUrl +"/smartpayment/settlementobj/del"  


/** 管理员/商户添加银行卡(POST) */
zBankNew = zMainUrl +"/smartmerchant/merchant/bank/new"  
/** 管理员/商户银行卡列表(POST) */
zBankQuery = zMainUrl +"/smartmerchant/merchant/bank/query"  
/** 管理员/商户设置默认卡(POST) */
zBankUpdateIddef= zMainUrl +"/smartmerchant/merchant/bank/update/iddef" 
/**管理员/商户获取默认结算卡(POST) */
zBankQueryIddef = zMainUrl +"/smartmerchant/merchant/bank/query/iddef"  
/**管理员/商户删除银行卡(POST) */
zBankDel= zMainUrl +"/smartmerchant/merchant/bank/del/cardno"  

/**平台外放通道查询 */
zplatformChannellAll= zMainUrl +"/smartpayment/platform/pay/channel/all"  

/**平台上游通道查询*/
zplatformRealchannelAll= zMainUrl +"/smartpayment/platform/pay/realchannel/all"  

/**商家外方所有渠道查询*/
zChannelQueryall= zMainUrl +"/smartmerchant/merchant/channel/query" 


/**代理商查询分润接口*/
zAgentProfitHistory= zMainUrl +"/smartclearing/agent/profit/history" 
/**新增提现的设置*/
zWithdrawConditionNew= zMainUrl +"/smartpayment/pay/withdraw/condition/new"
/**查询提现的配置*/
zWithdrawConditionQuery= zMainUrl +"/smartpayment/pay/withdraw/condition/query"  
/**更新结算周期*/
zUpdatesettleperoid= zMainUrl +"/smartmerchant/merchant/updatesettleperoid" 
 /**发送验证码*/
zMerchantSendcode= zMainUrl +"/smartmerchant/merchant/sendcode" 
  /** 更新支付密码*/
zUpdatepaypass= zMainUrl +"/smartmerchant/merchant/updatepaypass"   
/** 验证支付密码是否正确（提现的时候验证）*/
zValidPaypass= zMainUrl +"/smartmerchant/merchant/valid/paypass"  
/** 按天按月获取交易统计*/
zStatisLastest= zMainUrl +"/smartclearing/merchant/statis/lastest"  







