package goods_issue.controller;

import goods_issue.dataAccess.IssuesDAO;
import goods_issue.model.Issues;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PagingSearchIssuesControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        IssuesDAO issuesDao = new IssuesDAO();
        
        String dataSearch = request.getParameter("dataSearch");
        String searchValue = request.getParameter("data-search");  
        if (searchValue != null) {
            dataSearch = searchValue;
        }
        
        List<Issues> issuesListSearch = issuesDao.searchByName(dataSearch);

        String indexPage = request.getParameter("index");
        if (indexPage == null) {
            indexPage = "1";
        }

        int index = Integer.parseInt(indexPage);
        int pageLimit = 10;
        int iCount = !issuesListSearch.isEmpty() ? issuesListSearch.size() : issuesDao.countTotal();

        int endPage = iCount / pageLimit;
        if ((endPage == 0 || endPage % pageLimit != 0) && endPage != 1) {
            endPage++;
        }

        int itemStart = (index - 1) * pageLimit + 1;
        int itemEnd;
        if (index == endPage) {
            itemEnd = iCount;
        } else {
            itemEnd = index * pageLimit;
        }
        
        List<Issues> issuesList = new ArrayList<>();
        if (dataSearch != null) {
            issuesList = issuesDao.pagingIssues(dataSearch,index, pageLimit); 
            request.setAttribute("data-search", dataSearch);
        } else {
            issuesList = issuesDao.pagingIssues(index, pageLimit); 
        }
        
        request.setAttribute("issuesList", issuesList);
        request.setAttribute("iCount", iCount);
        request.setAttribute("itemStart", itemStart);
        request.setAttribute("itemEnd", itemEnd);
        request.setAttribute("endPage", endPage);
        request.setAttribute("index", index);
        request.getRequestDispatcher("issues.jsp").forward(request, response);
    
       
                
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
