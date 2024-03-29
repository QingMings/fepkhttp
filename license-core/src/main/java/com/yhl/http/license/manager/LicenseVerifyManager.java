package com.yhl.http.license.manager;


import com.yhl.http.exceptions.LicenseException;
import com.yhl.http.model.LicenseVerifyParam;
import com.yhl.http.util.ParamInitUtils;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;
import de.schlichtherle.license.NoLicenseInstalledException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


/**
 * License校验类
 *
 * @author Rong.Jia
 * @date 2022/03/10
 */

public class LicenseVerifyManager {
  private static final Logger logger = LoggerFactory.getLogger(LicenseVerifyManager.class);

    /**
     * 安装License证书
     * @param param License校验类需要的参数
     * @return {@link LicenseContent}
     */
    public static synchronized LicenseContent install(LicenseVerifyParam param){
        try{
            // 初始化License证书参数
            LicenseParam licenseParam = ParamInitUtils.initLicenseParam(param);

            //走自定义的Lic管理
            LicenseCustomManager licenseManager = new LicenseCustomManager(licenseParam);

            // 获取要安装的证书文件
            File licenseFile = new File(param.getLicensePath());

            // 如果之前安装过证书，先卸载之前的证书 == 给null
            licenseManager.uninstall();

            // 开始安装
            return licenseManager.install(licenseFile);
        }catch (Exception e){
            logger.error("证书安装异常", e);
            throw new LicenseException("证书安装异常", e);
        }
    }

    /**
     * 卸载证书
     *
     * @param param License校验类需要的参数
     */
    public static synchronized void uninstall(LicenseVerifyParam param) {
        try{
            // 初始化License证书参数
            LicenseParam licenseParam = ParamInitUtils.initLicenseParam(param);

            //走自定义的Lic管理
            LicenseCustomManager licenseManager = new LicenseCustomManager(licenseParam);

            // 如果之前安装过证书，先卸载之前的证书 == 给null
            licenseManager.uninstall();
        }catch (Exception ignored){}
    }

    /**
     * 校验License证书
     *
     * @param param License校验类需要的参数
     * @return {@link LicenseContent}
     */
    public static LicenseContent verify(LicenseVerifyParam param){

        // 初始化License证书参数
        LicenseParam licenseParam = ParamInitUtils.initLicenseParam(param);

        // 创建License证书管理器对象
        LicenseManager licenseManager = new LicenseCustomManager(licenseParam);

        // 开始校验证书
        try {
            return licenseManager.verify();
        }catch (NoLicenseInstalledException ex){
            logger.error("证书未安装!", ex);
            throw new LicenseException("证书未安装, 请检查证书", ex);
        } catch (Exception e){
            logger.error("证书校验不通过 {}", e.getMessage());
            throw new LicenseException("证书校验不通过, 请检查证书是否合法", e);
        }
    }


}
