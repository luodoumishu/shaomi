package com.baidu.ueditor.hunter;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.MultiState;
import com.baidu.ueditor.define.State;
import com.xjj.cms.base.VO.CmsCC;

public class FileManager {

	private String dir = null;
	private String rootPath = null;
	private String[] allowFiles = null;
	private int count = 0;
	//请求地址
	//private String ip = "";
	public FileManager ( Map<String, Object> conf ) {

		this.rootPath = (String)conf.get( "rootPath" );
	//	this.dir = this.rootPath + (String)conf.get( "dir" );
		/*********yeyunfeng start***************/
		this.dir = conf.get("listUrl")+(String)conf.get("dir");
		/*********yeyunfeng end***************/
		this.allowFiles = this.getAllowFiles( conf.get("allowFiles") );
		this.count = (Integer)conf.get( "count" );
	//	this.ip = (String)conf.get( "ip" );
	}
	
	public State listFile ( int index ) throws Exception {
		
		
		File dir = new File( this.dir );
		State state = null;

		if ( !dir.exists() ) {
			return new BaseState( false, AppInfo.NOT_EXIST );
		}
		
		if ( !dir.isDirectory() ) {
			return new BaseState( false, AppInfo.NOT_DIRECTORY );
		}
		
		Collection<File> list = FileUtils.listFiles( dir, this.allowFiles, true );
		
		if ( index < 0 || index > list.size() ) {
			state = new MultiState( true );
		} else {
			Object[] fileList = Arrays.copyOfRange( list.toArray(), index, index + this.count );
			state = this.getState( fileList );
		}
		
		state.putInfo( "start", index );
		state.putInfo( "total", list.size() );
		return state;
	}
	
	private State getState ( Object[] files ) throws Exception {
		
		MultiState state = new MultiState( true );
		BaseState fileState = null;
		
		File file = null;
		
		for ( Object obj : files ) {
			if ( obj == null ) {
				break;
			}
			file = (File)obj;
			fileState = new BaseState( true );
			fileState.putInfo( "url", PathFormat.format( this.getPath( file ) ) );
			state.addState( fileState );
		}
		
		return state;
		
	}
	
	private String getPath ( File file ) throws Exception{
		
		String path = file.getAbsolutePath();
		/*********yeyunfeng start***************/
		String upUrl = com.xjj.cms.base.util.PropertiesUtil.getProperty(
				CmsCC.WEB_CONFIG_ZH, CmsCC.ARTICLE_AFFIX_PATH);
		/*String upUi = com.xjj.cms.base.util.PropertiesUtil.getProperty(
				CmsCC.WEB_CONFIG_ZH, CmsCC.ARTICLE_AFFIX_UI);*/
		path = path.replace(upUrl, "");
		/*********yeyunfeng end***************/
		//return path.replace( this.rootPath, "/" );
		String str=path.replace(this.rootPath.replaceAll("\\/", "\\\\"), "\\" );
		//str = this.ip+upUi+str;
		//str = upUi+str;
		return str;
	}
	
	private String[] getAllowFiles ( Object fileExt ) {
		
		String[] exts = null;
		String ext = null;
		
		if ( fileExt == null ) {
			return new String[ 0 ];
		}
		
		exts = (String[])fileExt;
		
		for ( int i = 0, len = exts.length; i < len; i++ ) {
			
			ext = exts[ i ];
			exts[ i ] = ext.replace( ".", "" );
			
		}
		
		return exts;
		
	}
	
}
