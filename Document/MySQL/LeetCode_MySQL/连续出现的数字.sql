表：Logs

+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| id          | int     |
| num         | varchar |
+-------------+---------+
在 SQL 中，id 是该表的主键。
id 是一个自增列。


找出所有至少连续出现三次的数字。

返回的结果表中的数据可以按 任意顺序 排列。

结果格式如下面的例子所示：



示例 1:

输入：
Logs 表：
+----+-----+
| id | num |
+----+-----+
| 1  | 1   |
| 2  | 1   |
| 3  | 1   |
| 4  | 2   |
| 5  | 1   |
| 6  | 2   |
| 7  | 2   |
+----+-----+
输出：
Result 表：
+-----------------+
| ConsecutiveNums |
+-----------------+
| 1               |
+-----------------+
解释：1 是唯一连续出现至少三次的数字。


# Write your MySQL query statement below

select distinct ConsecutiveNums from
    (
        select  a.num as ConsecutiveNums from (select id+2 as id,num from Logs) a left join (select id+1 as id,num from Logs) b on a.id=b.id and a.num=b.num left join (select id,num from Logs) c on b.id=c.id and b.num=c.num
        where b.num is not null and c.num is not null
    ) cqw
