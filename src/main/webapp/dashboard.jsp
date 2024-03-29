
<%-- 
    Document   : admin
    Created on : Nov 21, 2023, 4:14:02 PM
    Author     : Trong Huy
--%>

<%@page import="java.util.*"%>
<%@page import="goods_issue.model.*" %>
<%@page import="goods_issue.dataAccess.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    User user = (User) request.getSession().getAttribute("admin");
    UserDAO userDao = new UserDAO();
    ProductDAO productDao = new ProductDAO();
    IssuesDAO issuesDao = new IssuesDAO();

    String dataSearch = request.getParameter("data-search");
    List<Product> productListSearch = productDao.searchAllByName(dataSearch);
    List<User> userListSearch = userDao.searchByName(dataSearch);
    List<Issues> issuesList = issuesDao.selectAllIssues();
    List<Model> modelListMonth = issuesDao.countExportedByMonth();
    List<Model> modelListDays = issuesDao.countExportedByDays();

    int jan = 0, feb = 0, mar = 0, apr = 0, may = 0, jun = 0, jul = 0, aug = 0, sep = 0, oct = 0, nov = 0, dec = 0;
    for (Model m : modelListMonth) {
        if (m.getMonth().equals("1")) {
            jan = Integer.parseInt(m.getTotal_products());
        }
        if (m.getMonth().equals("2")) {
            feb = Integer.parseInt(m.getTotal_products());
        }
        if (m.getMonth().equals("3")) {
            mar = Integer.parseInt(m.getTotal_products());
        }
        if (m.getMonth().equals("4")) {
            apr = Integer.parseInt(m.getTotal_products());
        }
        if (m.getMonth().equals("5")) {
            may = Integer.parseInt(m.getTotal_products());
        }
        if (m.getMonth().equals("6")) {
            jun = Integer.parseInt(m.getTotal_products());
        }
        if (m.getMonth().equals("7")) {
            jul = Integer.parseInt(m.getTotal_products());
        }
        if (m.getMonth().equals("8")) {
            aug = Integer.parseInt(m.getTotal_products());
        }
        if (m.getMonth().equals("9")) {
            sep = Integer.parseInt(m.getTotal_products());
        }
        if (m.getMonth().equals("10")) {
            oct = Integer.parseInt(m.getTotal_products());
        }
        if (m.getMonth().equals("11")) {
            nov = Integer.parseInt(m.getTotal_products());
        }
        if (m.getMonth().equals("12")) {
            dec = Integer.parseInt(m.getTotal_products());
        }
    }

    int year2020 = 0, year2021 = 0, year2022 = 0, year2023 = 0, year2024 = 0;
    for (Model m : modelListMonth) {
        if (m.getYear().equals("2020")) {
            year2020 += Integer.parseInt(m.getTotal_products());
        }
        if (m.getYear().equals("2021")) {
            year2021 += Integer.parseInt(m.getTotal_products());
        }
        if (m.getYear().equals("2022")) {
            year2022 += Integer.parseInt(m.getTotal_products());
        }
        if (m.getYear().equals("2023")) {
            year2023 += Integer.parseInt(m.getTotal_products());
        }
        if (m.getYear().equals("2024")) {
            year2024 += Integer.parseInt(m.getTotal_products());
        }
    }

    int firstTotal = 0, secondTotal = 0, thirdTotal = 0, fourthTotal = 0, fifthTotal = 0, sixthTotal = 0, seventhTotal = 0;
    String first = null, second = null, third = null, fourth = null, fifth = null, sixth = null, seventh = null;
//        if index(0) = 3 => first = "tuesday" firstTotal =d.getTotal

    for (Model d : modelListDays) {
        if (modelListDays.get(0).getDayOfWeek().equals("2")) {
            first = "Mon";
            second = "Sun";
            third = "Sat";
            fourth = "Fri";
            fifth = "Thu";
            sixth = "Wed";
            seventh = "Tue";
            firstTotal = Integer.parseInt(modelListDays.get(0).getTotal_products());
            secondTotal = Integer.parseInt(modelListDays.get(1).getTotal_products());
            thirdTotal = Integer.parseInt(modelListDays.get(2).getTotal_products());
            fourthTotal = Integer.parseInt(modelListDays.get(3).getTotal_products());
            fifthTotal = Integer.parseInt(modelListDays.get(4).getTotal_products());
            sixthTotal = Integer.parseInt(modelListDays.get(5).getTotal_products());
            seventhTotal = Integer.parseInt(modelListDays.get(6).getTotal_products());
        }
        if (modelListDays.get(0).getDayOfWeek().equals("3")) {
            first = "Tue";
            second = "Mon";
            third = "Sun";
            fourth = "Sat";
            fifth = "Fri";
            sixth = "Thu";
            seventh = "Wed";
            firstTotal = Integer.parseInt(modelListDays.get(0).getTotal_products());
            secondTotal = Integer.parseInt(modelListDays.get(1).getTotal_products());
            thirdTotal = Integer.parseInt(modelListDays.get(2).getTotal_products());
            fourthTotal = Integer.parseInt(modelListDays.get(3).getTotal_products());
            fifthTotal = Integer.parseInt(modelListDays.get(4).getTotal_products());
            sixthTotal = Integer.parseInt(modelListDays.get(5).getTotal_products());
            seventhTotal = Integer.parseInt(modelListDays.get(6).getTotal_products());
        }
        if (modelListDays.get(0).getDayOfWeek().equals("4")) {
            first = "Wed";
            second = "Tue";
            third = "Mon";
            fourth = "Sun";
            fifth = "Sat";
            sixth = "Fri";
            seventh = "Thu";
            firstTotal = Integer.parseInt(modelListDays.get(0).getTotal_products());
            secondTotal = Integer.parseInt(modelListDays.get(1).getTotal_products());
            thirdTotal = Integer.parseInt(modelListDays.get(2).getTotal_products());
            fourthTotal = Integer.parseInt(modelListDays.get(3).getTotal_products());
            fifthTotal = Integer.parseInt(modelListDays.get(4).getTotal_products());
            sixthTotal = Integer.parseInt(modelListDays.get(5).getTotal_products());
            seventhTotal = Integer.parseInt(modelListDays.get(6).getTotal_products());
        }
        if (modelListDays.get(0).getDayOfWeek().equals("5")) {
            first = "Thu";
            second = "Wed";
            third = "Tue";
            fourth = "Mon";
            fifth = "Sun";
            sixth = "Sat";
            seventh = "Fri";
            firstTotal = Integer.parseInt(modelListDays.get(0).getTotal_products());
            secondTotal = Integer.parseInt(modelListDays.get(1).getTotal_products());
            thirdTotal = Integer.parseInt(modelListDays.get(2).getTotal_products());
            fourthTotal = Integer.parseInt(modelListDays.get(3).getTotal_products());
            fifthTotal = Integer.parseInt(modelListDays.get(4).getTotal_products());
            sixthTotal = Integer.parseInt(modelListDays.get(5).getTotal_products());
            seventhTotal = Integer.parseInt(modelListDays.get(6).getTotal_products());
        }
        if (modelListDays.get(0).getDayOfWeek().equals("6")) {
            first = "Fri";
            second = "Thu";
            third = "Wed";
            fourth = "Tue";
            fifth = "Mon";
            sixth = "Sun";
            seventh = "Sat";
            firstTotal = Integer.parseInt(modelListDays.get(0).getTotal_products());
            secondTotal = Integer.parseInt(modelListDays.get(1).getTotal_products());
            thirdTotal = Integer.parseInt(modelListDays.get(2).getTotal_products());
            fourthTotal = Integer.parseInt(modelListDays.get(3).getTotal_products());
            fifthTotal = Integer.parseInt(modelListDays.get(4).getTotal_products());
            sixthTotal = Integer.parseInt(modelListDays.get(5).getTotal_products());
            seventhTotal = Integer.parseInt(modelListDays.get(6).getTotal_products());
        }
        if (modelListDays.get(0).getDayOfWeek().equals("7")) {
            first = "Sat";
            second = "Fri";
            third = "Thu";
            fourth = "Wed";
            fifth = "Tue";
            sixth = "Mon";
            seventh = "Sun";
            firstTotal = Integer.parseInt(modelListDays.get(0).getTotal_products());
            secondTotal = Integer.parseInt(modelListDays.get(1).getTotal_products());
            thirdTotal = Integer.parseInt(modelListDays.get(2).getTotal_products());
            fourthTotal = Integer.parseInt(modelListDays.get(3).getTotal_products());
            fifthTotal = Integer.parseInt(modelListDays.get(4).getTotal_products());
            sixthTotal = Integer.parseInt(modelListDays.get(5).getTotal_products());
            seventhTotal = Integer.parseInt(modelListDays.get(6).getTotal_products());
        }
        if (modelListDays.get(0).getDayOfWeek().equals("1")) {
            first = "Sun";
            second = "Sat";
            third = "Fri";
            fourth = "Thu";
            fifth = "Wed";
            sixth = "Tue";
            seventh = "Mon";
            firstTotal = Integer.parseInt(modelListDays.get(0).getTotal_products());
            secondTotal = Integer.parseInt(modelListDays.get(1).getTotal_products());
            thirdTotal = Integer.parseInt(modelListDays.get(2).getTotal_products());
            fourthTotal = Integer.parseInt(modelListDays.get(3).getTotal_products());
            fifthTotal = Integer.parseInt(modelListDays.get(4).getTotal_products());
            sixthTotal = Integer.parseInt(modelListDays.get(5).getTotal_products());
            seventhTotal = Integer.parseInt(modelListDays.get(6).getTotal_products());
        }
    }

    int pCount = !productListSearch.isEmpty() ? productListSearch.size() : productDao.countTotal();
    int cCount = !userListSearch.isEmpty() ? userListSearch.size() : userDao.countTotal();
    int eCount = !issuesList.isEmpty() ? issuesList.size() : issuesDao.selectAllIssues().size();

%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Admin|Grocerymart</title>
        <!-- Favicon -->
        <link rel="apple-touch-icon" sizes="76x76" href="./assets/favicon/apple-touch-icon.png" />
        <link rel="icon" type="image/png" sizes="32x32" href="./assets/favicon/favicon-32x32.png" />
        <link rel="icon" type="image/png" sizes="16x16" href="./assets/favicon/favicon-16x16.png" />
        <link rel="manifest" href="./assets/favicon/site.webmanifest" />
        <link rel="mask-icon" href="./assets/favicon/safari-pinned-tab.svg" color="#5bbad5" />
        <meta name="msapplication-TileColor" content="#da532c" />
        <meta name="theme-color" content="#ffffff" />
        <!-- Fonts -->


        <!-- Stylesheets -->
        <link rel="stylesheet" href="./assets/css/main.css" />
        <link rel="stylesheet" href="./assets/css/admin.css" />
        <link rel="stylesheet" href="./assets/fonts/stylesheet.css" />
        <link rel="stylesheet" href="./assets/css/bootstrap.min.css" />

        <!-- Scripts -->
        <script src="./assets/js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
    </head>
    <body>
        <%            
            if (user == null) {
        %>
        <h3 style='color:crimson; font-size: 30px; font-weight: 500; text-align: center'>You are not logged into the system! <a href='./index.jsp'>Sign In</a></h3>")
        <%} else {%> 
        <!-- Sidebar -->
        <%
            request.setAttribute("page", "dashboard");
        %>
        <jsp:include page="assets/templates/sidebar.jsp" /> 
        <!-- Navbar -->
        <jsp:include page="assets/templates/header.jsp" /> 
        <!-- Main -->
        <main class="main">
            <div class="row">
                <div class="col-md-12 mb-4 mt-1">
                    <div class="dashboard__heading">
                        <h4 class="admin__heading">Overview</h4>
                    </div>
                </div>
                <div class="col-lg-8 col-md-12">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="card">
                                <div class="card-body">
                                    <div class="card-content">
                                        <div class="">
                                            <p class="card-content-p">Total Products</p>
                                            <div class="card-text">
                                                <h5 class="card-text-h5"><b class="count-animate"><%=pCount%> <br> products</b></h5>
                                                <a href='paging-search-product' class="card-text-a">View all products</a>
                                            </div>                            
                                        </div>
                                    </div>
                                </div>
                            </div>   
                        </div>
                        <div class="col-md-4">
                            <div class="card">
                                <div class="card-body">
                                    <div class="card-content">
                                        <div class="">
                                            <p class="card-content-p">Total Exporteds</p>
                                            <div class="card-text">
                                                <h5 class="card-text-h5"><b class="count-animate"><%=eCount%> <br> exporteds</b></h5>
                                                <a href='/Goods_Issue/issues.jsp' class="card-text-a">View all exporteds</a>
                                            </div>                            
                                        </div>
                                    </div>
                                </div>
                            </div>   
                        </div>
                        <div class="col-md-4">
                            <div class="card">
                                <div class="card-body">
                                    <div class="card-content">
                                        <div class="">
                                            <p class="card-content-p">Customers</p>
                                            <div class="card-text">
                                                <h5 class="card-text-h5"><b class="count-animate"><%=cCount%> <br> customers</b></h5>
                                                <a href='/Goods_Issue/admin.jsp' class="card-text-a">View all customers</a>
                                            </div>                            
                                        </div>
                                    </div>
                                </div>
                            </div>   
                        </div>
                        <div class="col-md-12">
                            <div class="card">
                                <div class="card-body js-tabs" style="position: relative;">
                                    <div class="dashboard__heading">
                                        <h4 class="card-body-h4">Total Products Exported</h4>
                                        <div class="dashboard-chart-heading">

                                            <!-- Tab List -->
                                            <div class="dashboard-chart-options" id="list-tab" role="tablist">

                                                <button class="list-group-item" id="btn7Days">7 Days</button>
                                                <button class="list-group-item" id="btnMonthly">Monthly</button>
                                                <button class="list-group-item" id="btnYearly">Yearly</button>
                                            </div>
                                        </div>
                                            </div>
                                    </div>
                                    <canvas id="myChart" style="width:100%;max-width:800px;height: 500px; margin: 0 auto"></canvas>
                                    <script>
                                        // Define the chart data for the 7 Days, Monthly, and Yearly charts
                                        const chartData = {
                                            '7Days': {
                                                xValues: ["<%=seventh%>", "<%=sixth%>", "<%=fifth%>", "<%=fourth%>", "<%=third%>", "<%=second%>", "<%=first%>"],
                                                yValues: [<%=seventhTotal%>, <%=sixthTotal%>, <%=fifthTotal%>, <%=fourthTotal%>, <%=thirdTotal%>, <%=secondTotal%>, <%=firstTotal%>],
                                                barColors: ["orange", "orange", "orange", "orange", "orange", "orange", "orange"]
                                            },
                                            'Monthly': {
                                                xValues: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
                                                yValues: [<%=jan%>, <%=feb%>, <%=mar%>, <%=apr%>, <%=may%>, <%=jun%>, <%=jul%>, <%=aug%>, <%=sep%>, <%=oct%>, <%=nov%>, <%=dec%>],
                                                barColors: ["orange", "orange", "orange", "orange", "orange", "orange", "orange", "orange", "orange", "orange", "orange", "orange"]
                                            },
                                            'Yearly': {
                                                xValues: ["2020", "2021", "2022", "2023", "2024"],
                                                yValues: [<%=year2020%>, <%=year2021%>, <%=year2022%>, <%=year2023%>,<%=year2024%>],
                                                barColors: ["orange", "orange", "orange", "orange", "orange"]
                                            }
                                        };

                                        // Initialize the chart
                                        let myChart = null;

                                                // Function to create a chart
                                                function createChart(type) {
                                                    const ctx = document.getElementById('myChart').getContext('2d');
                                            // If a chart already exists, destroy it to make way for the new one
                                            if (myChart) {
                                                myChart.destroy();
                                            }
                                            
                                                    const data = chartData[type];
                                            myChart = new Chart(ctx, {
                                                type: 'bar',
                                                data: {
                                                    labels: data.xValues,
                                                    datasets: [{
                                                            backgroundColor: data.barColors,
                                                            data: data.yValues
                                                        }]
                                                },
                                                    options: {
                                                    // Add your options here
                                                    legend: {display: false},
                                                    title: {
                                                        display: true,
                                                        text: "Grocerymart exported 2024"
                                                    }
                                                }
                                            });
                                        }
                                    
                                     function updateActiveButton(activeButtonId) {
                                            // List of all buttons
                                            const buttons = ['btn7Days', 'btnMonthly', 'btnYearly'];

                                            // Remove the 'active' class from all buttons and then add it to the clicked one
                                            buttons.forEach(buttonId => {
                                                const button = document.getElementById(buttonId);
                                                if (buttonId === activeButtonId) {
                                                    button.classList.add('active');
                                                } else {
                                                    button.classList.remove('active');
                                                }
                                            });
                                        }

                                        document.getElementById('btn7Days').addEventListener('click', () => {
                                            createChart('7Days');
                                            updateActiveButton('btn7Days');
                                        });
                                        document.getElementById('btnMonthly').addEventListener('click', () => {
                                            createChart('Monthly');
                                            updateActiveButton('btnMonthly');
                                        });
                                        document.getElementById('btnYearly').addEventListener('click', () => {
                                            createChart('Yearly');
                                            updateActiveButton('btnYearly');
                                        });

                                        // Initially display the Monthly chart and set it as active
                                        createChart('Monthly');
                                        updateActiveButton('btnMonthly');
                                    </script>

                                    <svg id="SvgjsSvg1113" width="2" height="0" xmlns="http://www.w3.org/2000/svg" version="1.1" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:svgjs="http://svgjs.dev" style="overflow: hidden; top: -100%; left: -100%; position: absolute; opacity: 0;">
                                    <defs id="SvgjsDefs1114"></defs>
                                    <polyline id="SvgjsPolyline1115" points="0,0"></polyline>
                                    <path id="SvgjsPath1116" d="M0 0 "></path>
                                    </svg>
                                    <div class="resize-triggers">
                                        <div class="expand-trigger">
                                            <!--                                            <div style="width: 799px; height: 351px;"></div>-->
                                        </div>
                                        <div class="contract-trigger"></div></div></div>
                            </div>   
                        
                    </div>
                </div>

                <div class="col-lg-4 col-md-8">
                    <div class="card">
                        <div class="card-body" style="position: relative;">
                            <h4 class="card-body-h4 popular-categories-h4">Popular Categories</h4>
                            <canvas id="myCircleChart" style="width:100%;max-width:500px; height: 450px; margin-top: 60px"></canvas>

                            <script>
                                var totalMobiles = <%=productDao.countTotalCate("mobile")%>;
                                var totalLaptops = <%=productDao.countTotalCate("laptop")%>;
                                var totalTablets = <%=productDao.countTotalCate("tablet")%>;
                                const xValues = ["Moblie", "Laptop", "Tablet"];
                                const yValues = [totalMobiles, totalLaptops, totalTablets];
                                const barColors = [
                                    "#b91d47",
                                    "#00aba9",
                                    "#2b5797",
                                ];

                                new Chart("myCircleChart", {
                                    type: "doughnut",
                                    data: {
                                        labels: xValues,
                                        datasets: [{
                                                backgroundColor: barColors,
                                                data: yValues
                                            }]
                                    },
                                    options: {
                                        title: {
                                            display: true,
                                            text: "2023 - 2024"
                                        }
                                    }
                                });
                            </script>

                            <div class="resize-triggers">
                                <div class="expand-trigger">
                                    <div style="width: 385px; height: 160px;"></div>
                                </div>
                                <div class="contract-trigger"></div></div></div>
                    </div>
                </div>
            </div>
        </main>
        <%}%>
    </body>
    <script>window.dispatchEvent(new Event("template-loaded"));</script>
    <script src='assets/js/chart.js'></script>
</html>
