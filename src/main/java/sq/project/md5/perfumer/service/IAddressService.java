package sq.project.md5.perfumer.service;

import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.AddressRequest;
import sq.project.md5.perfumer.model.dto.resp.AddressResponse;
import sq.project.md5.perfumer.model.entity.Address;
import sq.project.md5.perfumer.model.entity.Users;

import java.util.List;

public interface IAddressService {
    Address addNewAddress(AddressRequest address);
    List<AddressResponse> getUserAddresses() throws CustomException;
    AddressResponse getAddressById(Long id);
    void deleteAddressById(Long id) throws CustomException;

    Address getDefaultAddressForUser(Users user);

    Address findByIdAndUser(Long id, Users user);
}
