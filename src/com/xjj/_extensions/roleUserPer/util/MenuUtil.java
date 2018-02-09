package com.xjj._extensions.roleUserPer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.xjj._extensions.roleUserPer.model.Menu;
import com.xjj._extensions.roleUserPer.model.MenuTree;

public class MenuUtil {
	private static MenuUtil inst;
	public static MenuUtil getInst() {
		if (inst == null) {
			return new MenuUtil();
		} else {
			return inst;
		}
	}
	public List<Menu> getMenuList() {
		String a = this.getClass().getResource("/").getPath()+ "menu.txt";
		System.out.println(this.getClass().getResource("/").getPath());
		String path="";
		try {
			path = java.net.URLDecoder.decode(a,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		System.out.println("path="+path);
		List<Menu> menuList = new ArrayList<Menu>();
		File file = new File(path);
		if(file.isFile()&&file.exists()){			
			try {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file),"UTF8");
                BufferedReader bw = new BufferedReader(read);
				String line = null;
				// 因为不知道有几行数据，所以先存入list集合中
				int one = 0;
				int two = 0;
				int three = 0;
				String oneCode = "";//一级
				String twoCode = "";//二级
				String oneMark = "";//一级
				String twoMark = "";//二级
				int pri=0;
				while ((line = bw.readLine()) != null) {
					Menu menu = new Menu();
					//Map<String, String> map = new HashMap<String, String>();
					if(line.trim().length()>0&&!line.trim().substring(0,1).equals("#")){
						String[] arr = line.split(";");
						if(arr.length>0){
							if(len(arr[0])==1){
								menu.setMenu("one");
							}else if(len(arr[0])==2){
								menu.setMenu("two");
							}else if(len(arr[0])==3){
								menu.setMenu("three");
							}
							menu.setMenuName(arr[0].replace(".", ""));
							for(int i=1;i<arr.length;i++){
								String[] arrSub = arr[i].split("@");
								if(arrSub.length>1){
									if(arrSub[0].equals("img")){
										menu.setImg(arrSub[1]);
									}else if(arrSub[0].equals("url")){
										menu.setUrl(arrSub[1]);
									}else if(arrSub[0].equals("code")){
										menu.setCodeStr(arrSub[1]);
									}else if(arrSub[0].equals("module")){
										menu.setModule(arrSub[1]);
									}
								}
							}
						}
						if (menu.getMenu()!=null&&menu.getMenu().equals("one")) {
							pri++;
							one++;
							two = 0;
							three = 0;
							menu.setMark(one + "");// 标识
							menu.setPri(pri);
							oneCode = "";
							if(menu.getCodeStr()!=null&&menu.getCodeStr().trim().length()>0){
								oneCode = menu.getCodeStr();
							}
							oneMark = menu.getMark();
							menuList.add(menu);// 加一行......
						} else if (menu.getMenu()!=null&&menu.getMenu().equals("two")) {
							pri++;
							two++;
							three = 0;
							menu.setMark_up(one + "");// 上级标识
							menu.setMark(one+"."+two);// 标识
							menu.setPri(pri);
							if(menu.getMark_up().equals(oneMark)){
								if(!"".equals(oneCode)){
									if(menu.getCodeStr()==null){
										menu.setCodeStr(oneCode);
									}
								}
							}
							twoCode = "";
							if(menu.getCodeStr()!=null&&menu.getCodeStr().trim().length()>0){
								twoCode = menu.getCodeStr();
							}
							twoMark = menu.getMark();
							menuList.add(menu);// 加一行......
						}else if(menu.getMenu()!=null&&menu.getMenu().equals("three")){
							pri++;
							three++;
							menu.setMark_up(one+"."+two);//上级标识
							menu.setMark(one+"."+two+"."+three);//标识
							menu.setPri(pri);
							if(menu.getMark_up().equals(twoMark)){
								if(!"".equals(twoCode)){
									if(menu.getCodeStr()==null){
										menu.setCodeStr(twoCode);
									}
								}
							}
							menuList.add(menu);//加一行......
						}
					}
					
				}
				bw.close();
				read.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}  
		return menuList;
	}
	public List<MenuTree> getMenuTree(List<Menu> menuList,String[] code) {
		List<MenuTree> oneList = new ArrayList<MenuTree>();
		List<MenuTree> twoList = new ArrayList<MenuTree>();
		List<MenuTree> threeList = new ArrayList<MenuTree>();
		if(menuList!=null){
			boolean oneBoo = false;
			boolean twoBoo = false;
			MenuTree oneTree = null;
			MenuTree twoTree = null;
			MenuTree threeTree = null;
			for(int i=0;i<menuList.size();i++){
				if(menuList.get(i).getMenu().equals("one")){
					if(threeTree!=null){
						threeList.add(threeTree);
					}
					if(twoTree!=null){
						twoTree.setChild(threeList);
						twoList.add(twoTree);
					}
					if(oneTree!=null){
						oneTree.setChild(twoList);
						oneList.add(oneTree);
					}
					oneBoo = false;
					twoBoo = false;
					oneTree = null;
					twoTree = null;
					twoList = new ArrayList<MenuTree>();
					threeList = new ArrayList<MenuTree>();
					if(menuList.get(i).getCodeStr()==null){
						oneBoo = true;
					}else if(menuList.get(i).getCodeStr().length()>0){
						String[] arrCode = menuList.get(i).getCodeStr().split(",");
						if(arrCode.length>0){
							for(int j=0;j<arrCode.length;j++){
								if(arrCode[j]!=null&&arrCode[j].length()>0){
									if(code!=null&&code.length>0){
										for(int z=0;z<code.length;z++){
											if(arrCode[j].equals(code[z])){
												oneBoo = true;
											}
										}
									}
								}
							}
						}
					}
					if(oneBoo){
						oneTree = new MenuTree();
						if(menuList.get(i).getMenuName()!=null&&menuList.get(i).getMenuName().length()>0){
							oneTree.setMenuName(menuList.get(i).getMenuName());	
						}
						if(menuList.get(i).getImg()!=null&&menuList.get(i).getImg().length()>0){
							oneTree.setImg(menuList.get(i).getImg());	
						}
						if(menuList.get(i).getUrl()!=null&&menuList.get(i).getUrl().length()>0){
							oneTree.setUrl(menuList.get(i).getUrl());	
						}
					}
				}else if(menuList.get(i).getMenu().equals("two")){
					if(threeTree!=null){
						threeList.add(threeTree);
					}
					if(twoTree!=null){
						twoTree.setChild(threeList);
						twoList.add(twoTree);
					}
					threeList = new ArrayList<MenuTree>();
					twoBoo = false;
					twoTree = null;
					threeTree = null;
					if(oneBoo){
						if(menuList.get(i).getCodeStr()==null){
							twoBoo = true;
						}else if(menuList.get(i).getCodeStr().length()>=0){
							String[] arrCode = menuList.get(i).getCodeStr().split(",");
							if(arrCode.length>0){
								for(int j=0;j<arrCode.length;j++){
									if(arrCode[j]!=null&&arrCode[j].length()>0){
										if(code!=null&&code.length>0){
											for(int z=0;z<code.length;z++){
												if(arrCode[j].equals(code[z])){
													twoBoo = true;
												}
											}
										}
									}
								}
							}
						}
					}
					if(twoBoo){
						twoTree = new MenuTree();
						if(menuList.get(i).getMenuName()!=null&&menuList.get(i).getMenuName().length()>0){
							twoTree.setMenuName(menuList.get(i).getMenuName());	
						}
						if(menuList.get(i).getImg()!=null&&menuList.get(i).getImg().length()>0){
							twoTree.setImg(menuList.get(i).getImg());	
						}
						if(menuList.get(i).getUrl()!=null&&menuList.get(i).getUrl().length()>0){
							twoTree.setUrl(menuList.get(i).getUrl());	
						}
					}
				}else if(menuList.get(i).getMenu().equals("three")){
					if(threeTree!=null){
						threeList.add(threeTree);
					}
					threeTree = null;
					boolean threeBoo = false;
					if(twoBoo){
						if(menuList.get(i).getCodeStr()==null){
							threeBoo = true;
						}else if(menuList.get(i).getCodeStr().length()>=0){
							String[] arrCode = menuList.get(i).getCodeStr().split(",");
							if(arrCode.length>0){
								for(int j=0;j<arrCode.length;j++){
									if(arrCode[j]!=null&&arrCode[j].length()>0){
										if(code!=null&&code.length>0){
											for(int z=0;z<code.length;z++){
												if(arrCode[j].equals(code[z])){
													threeBoo = true;
												}
											}
										}
									}
								}
							}
						}
					}
					if(threeBoo){
						threeTree = new MenuTree();
						if(menuList.get(i).getMenuName()!=null&&menuList.get(i).getMenuName().length()>0){
							threeTree.setMenuName(menuList.get(i).getMenuName());	
						}
						if(menuList.get(i).getImg()!=null&&menuList.get(i).getImg().length()>0){
							threeTree.setImg(menuList.get(i).getImg());	
						}
						if(menuList.get(i).getUrl()!=null&&menuList.get(i).getUrl().length()>0){
							threeTree.setUrl(menuList.get(i).getUrl());	
						}
					}
				}
				if(i==menuList.size()-1){
					if(threeTree!=null){
						threeList.add(threeTree);
					}
					if(twoTree!=null){
						twoTree.setChild(threeList);
						twoList.add(twoTree);
					}
					if(oneTree!=null){
						oneTree.setChild(twoList);
						oneList.add(oneTree);
					}
				}
			}
		}
		
		return oneList;
	}
	
	public List<MenuTree> getMenuTree(List<Menu> menuList,String[] code,String[] module) {
		List<MenuTree> oneList = new ArrayList<MenuTree>();
		List<MenuTree> twoList = new ArrayList<MenuTree>();
		List<MenuTree> threeList = new ArrayList<MenuTree>();
		if(menuList!=null){
			boolean oneBoo = false;
			boolean twoBoo = false;
			MenuTree oneTree = null;
			MenuTree twoTree = null;
			MenuTree threeTree = null;
			for(int i=0;i<menuList.size();i++){
				if(menuList.get(i).getMenu().equals("one")){
					boolean moduleBoo = false;
					if(menuList.get(i).getModule()==null){
						moduleBoo = true;
					}else{
						if(module!=null&&module.length>0){
							for(int m=0;m<module.length;m++){
								if(menuList.get(i).getModule().length()>0&&menuList.get(i).getModule().equals(module[m])){
									moduleBoo = true;
								}
							}
						}
					}
					if(moduleBoo){
						if(threeTree!=null){
							threeList.add(threeTree);
						}
						if(twoTree!=null){
							twoTree.setChild(threeList);
							twoList.add(twoTree);
						}
						if(oneTree!=null){
							oneTree.setChild(twoList);
							oneList.add(oneTree);
						}
						oneBoo = false;
						twoBoo = false;
						oneTree = null;
						twoTree = null;
						twoList = new ArrayList<MenuTree>();
						threeList = new ArrayList<MenuTree>();
						if(menuList.get(i).getCodeStr()==null){
							oneBoo = true;
						}else if(menuList.get(i).getCodeStr().length()>0){
							String[] arrCode = menuList.get(i).getCodeStr().split(",");
							if(arrCode.length>0){
								for(int j=0;j<arrCode.length;j++){
									if(arrCode[j]!=null&&arrCode[j].length()>0){
										if(code!=null&&code.length>0){
											for(int z=0;z<code.length;z++){
												if(arrCode[j].equals(code[z])){
													oneBoo = true;
												}
											}
										}
									}
								}
							}
						}
						if(oneBoo){
							oneTree = new MenuTree();
							if(menuList.get(i).getMenuName()!=null&&menuList.get(i).getMenuName().length()>0){
								oneTree.setMenuName(menuList.get(i).getMenuName());	
							}
							if(menuList.get(i).getImg()!=null&&menuList.get(i).getImg().length()>0){
								oneTree.setImg(menuList.get(i).getImg());	
							}
							if(menuList.get(i).getUrl()!=null&&menuList.get(i).getUrl().length()>0){
								oneTree.setUrl(menuList.get(i).getUrl());	
							}
						}
					}
						
					
				}else if(menuList.get(i).getMenu().equals("two")){
					if(threeTree!=null){
						threeList.add(threeTree);
					}
					if(twoTree!=null){
						twoTree.setChild(threeList);
						twoList.add(twoTree);
					}
					threeList = new ArrayList<MenuTree>();
					twoBoo = false;
					twoTree = null;
					threeTree = null;
					if(oneBoo){
						if(menuList.get(i).getCodeStr()==null){
							twoBoo = true;
						}else if(menuList.get(i).getCodeStr().length()>=0){
							String[] arrCode = menuList.get(i).getCodeStr().split(",");
							if(arrCode.length>0){
								for(int j=0;j<arrCode.length;j++){
									if(arrCode[j]!=null&&arrCode[j].length()>0){
										if(code!=null&&code.length>0){
											for(int z=0;z<code.length;z++){
												if(arrCode[j].equals(code[z])){
													twoBoo = true;
												}
											}
										}
									}
								}
							}
						}
					}
					if(twoBoo){
						twoTree = new MenuTree();
						if(menuList.get(i).getMenuName()!=null&&menuList.get(i).getMenuName().length()>0){
							twoTree.setMenuName(menuList.get(i).getMenuName());	
						}
						if(menuList.get(i).getImg()!=null&&menuList.get(i).getImg().length()>0){
							twoTree.setImg(menuList.get(i).getImg());	
						}
						if(menuList.get(i).getUrl()!=null&&menuList.get(i).getUrl().length()>0){
							twoTree.setUrl(menuList.get(i).getUrl());	
						}
					}
				}else if(menuList.get(i).getMenu().equals("three")){
					if(threeTree!=null){
						threeList.add(threeTree);
					}
					threeTree = null;
					boolean threeBoo = false;
					if(twoBoo){
						if(menuList.get(i).getCodeStr()==null){
							threeBoo = true;
						}else if(menuList.get(i).getCodeStr().length()>=0){
							String[] arrCode = menuList.get(i).getCodeStr().split(",");
							if(arrCode.length>0){
								for(int j=0;j<arrCode.length;j++){
									if(arrCode[j]!=null&&arrCode[j].length()>0){
										if(code!=null&&code.length>0){
											for(int z=0;z<code.length;z++){
												if(arrCode[j].equals(code[z])){
													threeBoo = true;
												}
											}
										}
									}
								}
							}
						}
					}
					if(threeBoo){
						threeTree = new MenuTree();
						if(menuList.get(i).getMenuName()!=null&&menuList.get(i).getMenuName().length()>0){
							threeTree.setMenuName(menuList.get(i).getMenuName());	
						}
						if(menuList.get(i).getImg()!=null&&menuList.get(i).getImg().length()>0){
							threeTree.setImg(menuList.get(i).getImg());	
						}
						if(menuList.get(i).getUrl()!=null&&menuList.get(i).getUrl().length()>0){
							threeTree.setUrl(menuList.get(i).getUrl());	
						}
					}
				}
				if(i==menuList.size()-1){
					if(threeTree!=null){
						threeList.add(threeTree);
					}
					if(twoTree!=null){
						twoTree.setChild(threeList);
						twoList.add(twoTree);
					}
					if(oneTree!=null){
						oneTree.setChild(twoList);
						oneList.add(oneTree);
					}
				}
			}
		}
		
		return oneList;
	}
	
	public int len(String str) {
		return str.length() - str.replace(".", "").length();
	}
	
	public ModelAndView jsonMenuTree(String codeStr) {
		List<Menu> list= getInst().getMenuList();
		String[] arr = codeStr!=null?codeStr.split(","):null;
		List<MenuTree> tree= getMenuTree(list,arr);
		return new ModelAndView().addObject(tree);
    }
	
	public ModelAndView jsonMenuTree(String codeStr,String moduleStr) {
		List<Menu> list= getInst().getMenuList();
		String[] codeArr = codeStr!=null?codeStr.split(","):null;
		String[] moduleArr = moduleStr!=null?moduleStr.split(","):null;
		List<MenuTree> tree= getMenuTree(list,codeArr,moduleArr);
		return new ModelAndView().addObject(tree);
    }
//	public static void main(String[] args) {
////		getInst().getMenuList();
////	    ModelAndView a = getInst().getMenuList("code1,code2");
////		//String path = "E:\\Workspaces\\MyEclipse 10\\ggyypt\\src\\menu.txt";
//		List<Menu> list= getInst().getMenuList();
//		for(int i=0;i<list.size();i++){
//			  Menu m = list.get(i);  
//			  String name="";
//			  try{
//				  name = java.net.URLDecoder.decode(m.getMenuName(),"UTF-8");
//				  
//		   //System.out.println(m.getModule()+","+m.getMenu()+","+name+","+m.getCodeStr());
//			  }catch(Exception e){}
//		  }
//		String codeStr = "code2,code1";
//		String[] codeArr = codeStr.split(",");
//		List<MenuTree> tree= getInst().getMenuTree(list,codeArr);
//		String moduleStr = "vacation,code1";
//		String[] moduleArr = moduleStr.split(",");
//		List<MenuTree> tree2= getInst().getMenuTree(list,codeArr,moduleArr);
//		System.out.println(tree);
//	}

}
