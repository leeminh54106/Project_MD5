package sq.project.md5.perfumer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sq.project.md5.perfumer.model.dto.resp.DashboardResponse;
import sq.project.md5.perfumer.service.IDashboardService;
import sq.project.md5.perfumer.service.IOrderService;
import sq.project.md5.perfumer.service.IProductService;
import sq.project.md5.perfumer.service.IUserService;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements IDashboardService {
    private final IUserService userService;
    private final IProductService productService;
    private final IOrderService orderService;
    @Override
    public DashboardResponse getDashboard() {
        DashboardResponse dashboard = new DashboardResponse();
        dashboard.setTotalUser(userService.findAll().size());
        dashboard.setTotalProduct(productService.getAllProduct().size());
        dashboard.setTotalOrder(orderService.findAll().size());
        dashboard.setRevenue(orderService.totalRevenue());

        return dashboard;
    }
}
