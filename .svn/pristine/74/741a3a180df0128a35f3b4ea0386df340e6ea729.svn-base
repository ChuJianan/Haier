package com.haier.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.haier.bean.Knowledges;
import com.haier.bean.UserInfo;

public class DBServer 
{
	private DBHelper dbhelper;
	public DBServer(Context context)
	{
		 this.dbhelper = new DBHelper(context);
	}
	/**
	 * 添加用户
	 * @param entity
	 */
	public void addUser(UserInfo entity)
	{
		  
		  SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
		  Object[] arrayOfObject = new Object[4];
		  arrayOfObject[0] = entity.getId();
		  arrayOfObject[1] = entity.getTotalScore();
		  arrayOfObject[2] = entity.getUserName();
		  arrayOfObject[3] = entity.getSessionId();
		  localSQLiteDatabase.execSQL("insert into userInfo(id,totalScore,userName,sessionId) values(?,?,?,?)", arrayOfObject);
		  localSQLiteDatabase.close();
	}
	/**
	 * 添加知识点
	 * @param entity
	 */
	public void addKnowledge(Knowledges entity)
	{
//		  SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
//		  Object[] arrayOfObject = new Object[3];
//		  arrayOfObject[0] = entity.getId();
//		  arrayOfObject[1] = entity.getName();
//		  arrayOfObject[2] = entity.getParentId();
//		  localSQLiteDatabase.execSQL("insert into knowledges(id,parentId,name) values(?,?,?)", arrayOfObject);
//		  localSQLiteDatabase.close();
			SQLiteDatabase db = dbhelper.getWritableDatabase();
			/* ContentValues */
			ContentValues cv = new ContentValues();
			cv.put("id", entity.getId());
			cv.put("parentId", entity.getName());
			cv.put("name", entity.getParentId());
			long row = db.insert("knowledges", null, cv);
			
	}
	
	/**
	 * 删除�?��班级
	 * 同时会删除students中该班级的学�?	 * @param class_id
	 */
	public void deleteClass(String class_id)
	{
	    SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
	    //设置了级联删除和级联更新
	    //在执行有级联关系的语句的时�?必须先设置�?PRAGMA foreign_keys=ON�?	    //否则级联关系默认失效
	    localSQLiteDatabase.execSQL("PRAGMA foreign_keys=ON");
	    Object[] arrayOfObject = new Object[1];
	    arrayOfObject[0] =class_id;
	    localSQLiteDatabase.execSQL("delete from classes where class_id=?", arrayOfObject);
	    localSQLiteDatabase.close();
	}
	
	/**
	 * 删除�?��学生
	 * @param student_id
	 */
	public void deleteStudent(String student_id)
	{
	    SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
	    Object[] arrayOfObject = new Object[1];
	    arrayOfObject[0] =student_id;
	    localSQLiteDatabase.execSQL("delete from students where student_id=?", arrayOfObject);
	    localSQLiteDatabase.close();
	}

	
	/**
	 * 修改学生信息
	* @param entity
	*//*
   public void updateStudentInfo(Student entity)
   {
	   SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
	   Object[] arrayOfObject = new Object[4];
	   
	   arrayOfObject[0] = entity.getStudentName();
	   arrayOfObject[1] = entity.getScore();
	   arrayOfObject[2] = entity.getClassId();
	   arrayOfObject[3] = entity.getStudentId();
   
	   localSQLiteDatabase.execSQL("update students set student_name=?,score=?,class_id=?  where student_id=?", arrayOfObject);
	   localSQLiteDatabase.close();
   }*/
	/*
	*//**
	 * 使用班级编号查找该班级所有学�?	 * @param classId
	 * @return
	 *//*
	public List<Student> findStudentsByClassId(String classId)
	{
		  List<Student> localArrayList=new ArrayList<Student>();
		  SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();    
		  Cursor localCursor = localSQLiteDatabase.rawQuery("select student_id, student_name ,score  from students  " +
		  		"where class_id=?  order by score desc", new String[]{classId});
		 
		  while (localCursor.moveToNext())
		  {
			  Student temp=new Student();
			  temp.setStudentId(localCursor.getString(localCursor.getColumnIndex("student_id")));
			  temp.setStudentName(localCursor.getString(localCursor.getColumnIndex("student_name")));
			  temp.setScore(localCursor.getString(localCursor.getColumnIndex("score")));
			  temp.setClassId(classId);
		      localArrayList.add(temp);
		  }
		  localSQLiteDatabase.close();
		  return localArrayList;
	 }
	
	*//**
	 * 使用班级名查找该班级�?��学生
	 * @param className
	 * @return
	 *//*
	public List<Student> findStudentsByClassName(String className)
	{
		  List<Student> localArrayList=new ArrayList<Student>();
		  SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();    
		  Cursor localCursor = localSQLiteDatabase.rawQuery("select student_id, student_name,score,classes.class_id from students,classes" +
		  		" where students.class_id=classes.class_id and classes.class_name =?  order by score asc" , new String[]{className});
		 
		  while (localCursor.moveToNext())
		  {
			  Student temp=new Student();
			  temp.setStudentId(localCursor.getString(localCursor.getColumnIndex("student_id")));
			  temp.setStudentName(localCursor.getString(localCursor.getColumnIndex("student_name")));
			  temp.setScore(localCursor.getString(localCursor.getColumnIndex("score")));
			  temp.setClassId(localCursor.getString(3));
		      localArrayList.add(temp);
		  }
		  localSQLiteDatabase.close();
		  return localArrayList;
	 }
	*//**
	 * 查找�?��学生
	 * @param className
	 * @return
	 *//*
	public List<Student> findAllStudents()
	{
		  List<Student> localArrayList=new ArrayList<Student>();
		  SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();    
		  Cursor localCursor = localSQLiteDatabase.rawQuery("select * from students " +
		  		"where 1=1  order by score desc ", null);
		  while (localCursor.moveToNext())
		  {
			  Student temp=new Student();
			  temp.setStudentId(localCursor.getString(localCursor.getColumnIndex("student_id")));
			  temp.setStudentName(localCursor.getString(localCursor.getColumnIndex("student_name")));
			  temp.setScore(localCursor.getString(localCursor.getColumnIndex("score")));
			  temp.setClassId(localCursor.getString(localCursor.getColumnIndex("class_id")));
		      localArrayList.add(temp);
		  }
		  localSQLiteDatabase.close();
		  return localArrayList;
	 }
	
	
	*//**
	 * 	取得�?��班级
	 * @return
	 *//*
	public List<Class> findAllClasses()
	{
		  List<Class> localArrayList=new ArrayList<Class>();
		  SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();    
		  Cursor localCursor = localSQLiteDatabase.rawQuery("select * from classes " +
		  		"where 1=1", null);
		  while (localCursor.moveToNext())
		  {
			  Class temp=new Class();
			  temp.setClassId(localCursor.getString(localCursor.getColumnIndex("class_id")));
			  temp.setClassName(localCursor.getString(localCursor.getColumnIndex("class_name")));
		      localArrayList.add(temp);
		  }
		  localSQLiteDatabase.close();
		  return localArrayList;
	 }
	
	*//**
	 * 成绩�?��
	 * @return
	 *//*
	public Student findMaxScoreStudent()
	{
		Student temp =new Student();
		SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();    
		Cursor localCursor = localSQLiteDatabase.rawQuery("select student_id,student_name,class_id,max(score)  from students  " +
		  		"where 1=1",null );
		localCursor.moveToFirst();
		temp.setStudentId(localCursor.getString(0));
		temp.setStudentName(localCursor.getString(1));
		temp.setClassId(localCursor.getString(2));
		temp.setScore(localCursor.getString(3));
		return temp;
	}
	

	
	*//**
	 * 查找是否有该学生
	 * @param studentId
	 * @return
	 *//*
	public boolean isStudentsExists(String studentId)
	{
		  SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();    
		  Cursor localCursor = localSQLiteDatabase.rawQuery("select count(*)  from students  " +
		  		"where student_id=?", new String[]{studentId});
		  localCursor.moveToFirst();
		  if(localCursor.getLong(0)>0)
			  return true;
		  else
			  return false;
	 }
	
	*//**
	 * 确认该班级是否存�?	 * @param classId
	 * @return
	 *//*
	public boolean isClassExists(String s)
	{
		  SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();    
		  Cursor localCursor = localSQLiteDatabase.rawQuery("select count(*)  from classes  " +
		  		"where class_id=? or class_name=?", new String[]{s,s});
		  localCursor.moveToFirst();
		  if(localCursor.getLong(0)>0)
			  return true;
		  else
			  return false;
	 }
*/
}
