package com.example.taskmanagerkanban.database;

import java.util.Date;
import java.util.UUID;

public  class DatabaseSchema {
    public static final String NAME="taskmanager.db";
    public static int VERSION=1;
    public class TaskTable{
        public static final String NAME="taskTable";
        public class TaskCols{
            public static final String UUID="id";
            public static final String TITLE="title";
            public static final String DESCRIPTION="description";
            public static final String TASKSTATE="taskState";
            public static final String DATE="date";
            public static final String CLOCK="clock";
        }
    }
    /*public class TaskState{
        public static final String NAME="taskState";
        public class TaskStateCols{
            public static final String ID="id";
            public static final String NAME="name";
        }
    }*/

}
