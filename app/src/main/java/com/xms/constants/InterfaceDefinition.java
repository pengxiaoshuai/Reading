package com.xms.constants;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 常量
 *
 * @author 彭其煊
 * @version 1.0
 * @date 2017.2.24
 */
public class InterfaceDefinition {

	/**
	 * 用户状态存储
	 *
	 * @author xk
	 * @version 1.0
	 * @date 2016年11月25日
	 */
	public static interface PreferencesUser {

		public static final String USER_ID = "User_Id";

		public static final String USER_PHONE = "User_phone";

		public static final String WELCOME_STATE = "Welcome_State";// 引导页状态

		public static final String LOGIN_STATE = "Login_State";// 登录状态

		public static final String LOGIN_MAIN = "Login_MAIN";// MainActivity的存在状态

		public static final String PHONE = "phone";// 登录的账号名字
		public static final String PASSWORD = "password";// 登录的账号密码
		public static final String TYPE = "type";//登陆账号的类型(21001001是购电用户，21001002是售电用户)
		public static final String ISCHECK = "ischeck";//判断是否联网检查企业信息的完善
		public static final String ISOVER="isover";//判断是否完善了企业信息

	}

	/**
	 * 报文数据项信息
	 *
	 * @author Only You
	 * @version 1.0
	 * @date 2016年1月18日
	 */
	public static interface ICommonKey {
		// 校验码
		public static final String TOKEN = "token";

		// key
		public static final String KEY = "n'NI&u#+lFA0y@;$6Wj=5(~9";

		// 报文编号
		public static final String PACK_NO = "pack_no";

		public static final String ROLES = "roles";

		// 发起方时间
		public static final String REQ_DATE = "date";

		// 用户ID
		public static final String USER_ID = "user_id";

		public static final String DEVICE_ID = "deviceId";

		// 报文体
		public static final String DATA = "data";
		public static final String ID = "ID";
		// 请求参数key
		public static final String REQUEST_DATA = "requestData";
	}

	public enum Preferences {
		Common, User;
	}


	/**
	 * 状态码定义
	 *
	 * @author xk
	 * @version 1.0
	 * @date 2016年11月30日
	 */
	public interface IStatusCode {
		// 响应成功
		public static final String SUCCESS = "000";

		// 校验码失效
		public static final String TOKEN_FAILURE = "011";

		// 参数错误
		public static final String PARAMS_ERROR = "002";
	}

	/**
	 * 分页信息
	 *
	 * @author Only You
	 * @version 1.0
	 * @date 2016年2月13日
	 */
	public interface IPage {
		// 一页显示多少条
		public static final String PAGESIZE = "pagesize";

		// 当前页码
		public static final String PAGE = "page";

		// 总记录数
		public static final String TOTAL_COUNTS = "totalcounts";

		// 数据集
		public static final String LIST = "list";

		public static final String DATA = "data";
		public static final String TYPE = "type";
	}

	/**
	 * 状态信息
	 *
	 * @author xk
	 * @version 1.0
	 * @date 2016年11月30日
	 */
	public interface IStatus {
		public static final String STATUS = "status";

		public static final String CODE = "code";

		public static final String MESSAGE = "message";

	}

	/**
	 * 请求唯一地址
	 *
	 * @author xk
	 * @version 1.0
	 * @date 2016年12月13日
	 */
	public interface IPath {
		public static final String BASE_URL = "http://etl.0791jr.com";
		public static final String BASE_URL0 = "http://120.203.228.227/store/";//10.10.0.203//http://192.168.0.4:8080/store/
		public static final String URL = "http://etl.0791jr.com//app.php?m=App&c=api&a=processing";
		public static final String URL0 = "http://10.10.0.203:8080/app/homepager.do";
		public static final String URL1 = "http://120.203.228.227/app/";//  :8080  http://120.203.228.227/app/ 192.168.20.245 http://192.168.20.229:8080/app/  http://192.168.20.245:8080/app/
	}

	public interface IuploadFile {
		public static final String UPLOAD_URL = "http://etl.0791jr.com//app.php?m=app&c=img&a=img";
	}

	/**
	 * 注册
	 *
	 * @author pqx
	 * @version 1.0
	 * @date 2016年12月13日
	 */
	public interface IRegister extends ICommonKey, IStatus, IuploadFile,
			IPath, IPage {
		public static final String PACKET_NO_DATA = "10001";
		public static final String TYPE = "TYPE";
		public static final String NAME = "NAME";
		public static final String MOBILE = "MOBILE";
		public static final String PASSWORD = "PASSWORD";
	}
	/**
	 * 账单结算列表
	 *
	 * @author pqx
	 * @version 1.0
	 * @date 2017年2月15日
	 */
	public interface Mzdjs extends ICommonKey, IStatus, IuploadFile,
			IPath, IPage {
		public static final String YEAR = "year";
	}
	/**
	 * 登陆
	 *
	 * @author pqx
	 * @version 1.0
	 * @date 201年1月16日
	 */
	public interface Login extends ICommonKey, IStatus, IuploadFile,
			IPath, IPage {
		public static final String MOBILE = "MOBILE";
		public static final String PASSWORD = "PASSWORD";
	}
	/**
	 * 负荷上报
	 *
	 * @author pqx
	 * @version 1.0
	 * @date 201年1月16日
	 */
	public interface Fhsb extends ICommonKey, IStatus, IuploadFile,
			IPath, IPage {
		public static final String NY = "ny";//年月
		public static final String PJFH = "pjfh";//平均负荷
		public static final String ZDFH = "zdfh";//最大负荷
		public static final String ZXFH = "zxfh";//最小负荷
		public static final String DYDL = "dydl";//当月电量
	}
	/**
	 * 列表单（业务咨询单21013001）
	 *
	 * @author pqx
	 * @version 1.0
	 * @date 201年1月16日
	 */
	public interface Liebiao extends ICommonKey, IStatus, IuploadFile,
			IPath, IPage {
		public static final String TYPE = "type";
	}
	/**
	 * 业务咨询
	 *
	 * @author pqx
	 * @version 1.0
	 * @date 201年1月16日
	 */
	public interface Operation extends ICommonKey, IStatus, IuploadFile,
			IPath, IPage {
		public static final String LXR = "lxr";
		public static final String LXRDH = "lxrdh";
		public static final String LXRSJ = "lxrsj";
		public static final String LXRDZ = "lxrdz";
		public static final String LXRYX = "lxryx";
		public static final String SM = "sm";
		public static final String SJMS = "sjms";//发生时间

	}
	/**
	 * 购电
	 *
	 * @author pqx
	 * @version 1.0
	 * @date 201年1月16日
	 *
	 */
	public interface BuyD extends ICommonKey, IStatus, IuploadFile,
			IPath, IPage {
		public static final String YXDL_DLNYS = "dlnys";//起始年月
		public static final String YXDL_DLNYE = "dlnye";//结束年月
		public static final String YXDL_YXDL = "yxdl";//意向电量
		public static final String YXDL_YXJC = "yxjc";//意向价差
		public static final String YXDL_LXR = "lxr";//联系人
		public static final String YXDL_LXFS = "lxfs";//联系方式
		public static final String YXDL_YXSM = "yxsm";//意向内容

		public static final String YXDL_TCXZ = "xytc";//意向内容
	}
	/**
	 * 购电(售电)企业信息修改
	 *
	 * @author pqx
	 * @version 1.0
	 * @date 201年1月16日
	 */
	public interface BuyMessage extends ICommonKey, IStatus, IuploadFile,
			IPath, IPage {
		public static final String NAME = "qymc";//企业名称
		public static final String CITY = "xzqy";//行政区域
		public static final String TYPE = "hyfl";//行业分类
		public static final String URL = "qywz";//企业网址
		public static final String ELECTRIC = "snddl";//上年度电量
		public static final String PROPERTY = "zcsx";//资产属性
		public static final String ADDRESS = "qydz";//企业地址
		public static final String MESSAGE = "qyjj";//企业简介
		//联系人信息
		public static final String XM = "xm";//姓名
		public static final String XB = "xb";//性别
		public static final String SZBM = "bm";//所在部门
		public static final String ZWZC = "zwzc";//职务/职称
		public static final String BGDH = "bgdh";//办公电话
		public static final String SJHM = "sjhm";//手机号码
		public static final String CZHM = "czhm";//传真号码
		public static final String YB = "yb";//邮编
		public static final String DZYX = "dzyx";//电子邮箱
		public static final String LXDZ = "lxdz";//联系地址
		public static final String BZ = "bz";//备注
		public static final String FRDB = "frdb";//法人代表
		public static final String KHYH = "khyh";//开户银行
		public static final String YHZH = "yhzh";//银行账号
		public static final String SWDJH = "swdjh";//税务登记号
		public static final String FDDZ = "fddz";//法定地址

		//售电信息
		public static final String FDJT = "fdjt";//发电集团
		public static final String DYDJ = "dydj";//电压等级
		public static final String ZJRL = "zjrl";//装机容量
		public static final String ZCSJ = "zcsj";//交易中心注册时间
		public static final String ZCZT = "zczt";//交易中心注册状态

		public static final String LY = "ly";//注册来源

	}

	/**
	 * 嘉瑞医生登录
	 *
	 * @author
	 * @version 1.0
	 * @date 2016年9月28日
	 */
	public interface ILogin extends ICommonKey, IStatus, IuploadFile, IPath,
			IPage {
		public static final String PACKET_NO_DATA = "10002";
		public static final String MOBILE = "mobile";
		public static final String PASSWORD = "password";
		public static final String TYPE = "type";
		public static final String NAME = "userame";
	}
	/**
	 * Apimanager接口
	 */
	public interface ApiManager{
		@POST("login.do")
		Call<String> getData(@Query("MOBILE")String name,@Query("PASSWORD")String pw);
	}

	public interface APIService{
		@POST("/list")
		Call<String> loadrepo();
	}

}
