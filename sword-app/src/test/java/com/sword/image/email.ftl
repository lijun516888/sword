<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head lang="zh">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>凯森时点战报</title>
    <style>
        * {
            margin: 0px;
            border: none;
            padding: 0px;
        }
        body {
            font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
            width: 100%;
            background: antiquewhite no-repeat;
        }
        table {
            border-collapse: collapse;
            text-align: center;
            width: 100%;
            background-color: #f6f4f0;
        }
        table,th,td {
            border: 1px solid black;
        }
        .header {
            font-size: 9px;
            color: black;
        }
        th {
            font-size: 9px;
            color: black;        }
        td {
            font-size: 9px;
            color: #082b15;
        }
    </style>
</head>
<body>
<table>
    <tr>
        <th colspan="10" class="header">凯森时点战报</th>
    </tr>
    <tr>
        <th style="font-family: '楷体'; font-weight: normal; font-size: 15px;">序号</th>
        <th>保险公司</th>
        <th>项目</th>
        <th>上线人数</th>
        <th>提交件</th>
        <th>提交保费</th>
        <th>成交件</th>
        <th>成交保费</th>
        <th>犹退件数</th>
        <th>犹退保费</th>
    </tr>
    <#list ds as d>
        <tr>
            <td <#if d.bxgs=='合计'>style="font-weight: bold"</#if>>${d.serial!''}</td>
            <td <#if d.bxgs=='合计'>style="font-weight: bold"</#if>>${d.bxgs!''}</td>
            <td <#if d.bxgs=='合计'>style="font-weight: bold"</#if>>${d.xm!''}</td>
            <td <#if d.bxgs=='合计'>style="font-weight: bold"</#if>>${d.onlineAgent!''}</td>
            <td <#if d.bxgs=='合计'>style="font-weight: bold"</#if>>${d.tjTotal!''}</td>
            <td <#if d.bxgs=='合计'>style="font-weight: bold"</#if>>${d.tjMoney!''}</td>
            <td <#if d.bxgs=='合计'>style="font-weight: bold"</#if>>${d.cjTotal!''}</td>
            <td <#if d.bxgs=='合计'>style="font-weight: bold"</#if>>${d.cjMoney!''}</td>
            <td <#if d.bxgs=='合计'>style="font-weight: bold"</#if>>${d.ytTotal!''}</td>
            <td <#if d.bxgs=='合计'>style="font-weight: bold"</#if>>${d.ytMoney!''}</td>
        </tr>
    </#list>
</table>
</body>
</html>