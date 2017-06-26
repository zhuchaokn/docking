import com.benmu.api.pojo.Order;
import com.benmu.platform.docking.annotations.Docking;
import com.benmu.platform.docking.annotations.DockingMapping;
import com.benmu.platform.docking.serializers.SerializeMethod;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zhuchao on 2017/6/2.
 */
@Docking("${host}")
public interface OrderDocking {

    @DockingMapping(value = "getOrder", response = SerializeMethod.XML)
    Order getOrder(@PathVariable String orderNo);
}
