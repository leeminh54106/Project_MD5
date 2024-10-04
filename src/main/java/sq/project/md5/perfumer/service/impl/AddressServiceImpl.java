package sq.project.md5.perfumer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sq.project.md5.perfumer.advice.SuccessException;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.AddressRequest;
import sq.project.md5.perfumer.model.dto.resp.AddressResponse;
import sq.project.md5.perfumer.model.entity.Address;
import sq.project.md5.perfumer.model.entity.Users;
import sq.project.md5.perfumer.repository.IAddressRepository;
import sq.project.md5.perfumer.service.IAddressService;
import sq.project.md5.perfumer.service.IUserService;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements IAddressService {

    private final IAddressRepository addressRepository;

    private final IUserService userService;

    @Override
    public Address addNewAddress(AddressRequest address) {
        Users user = userService.getCurrentLoggedInUser();
        if (address.getPhone() != null) {
            if (addressRepository.existsByPhone(address.getPhone())) {
                throw new IllegalArgumentException("Số điện thoại đã tồn tại");
            }
        }
        Address newAddress = Address.builder()
                .users(user)
                .fullAddress(address.getFullAddress())
                .phone(address.getPhone())
                .receiveName(address.getReceiveName())
                .isDefault(address.getIsDefault())

                .build();
        return addressRepository.save(newAddress);
    }

    @Override
    public List<AddressResponse> getUserAddresses() throws CustomException {
        Users user = userService.getCurrentLoggedInUser();

        List<Address> addresses = addressRepository.findByUsers(user);
        if (addresses.isEmpty()) {
            throw new SuccessException("Không có địa chỉ nào của người dùng");
        }

        List<AddressResponse> responseList = addresses.stream()
                .map( addr -> {
                    AddressResponse addressResponse = new AddressResponse();
                    addressResponse.setId(addr.getId());
                    addressResponse.setFullAddress(addr.getFullAddress());
                    addressResponse.setPhone(addr.getPhone());
                    addressResponse.setReceiveName(addr.getReceiveName());
                    addressResponse.setIsDefault(addr.getIsDefault());
                    return addressResponse;
                        }).collect(Collectors.toList());
        return responseList;
    }

    @Override
    public AddressResponse getAddressById(Long id) {
        Users user = userService.getCurrentLoggedInUser();
        Address address = addressRepository.findByIdAndUsers(id, user)
                .orElseThrow(() -> new NoSuchElementException("Không tồn tại địa chỉ có id: "+id));
        return AddressResponse.builder()
                .id(address.getId())
                .fullAddress(address.getFullAddress())
                .phone(address.getPhone())
                .receiveName(address.getReceiveName())
                .isDefault(address.getIsDefault())
                .build();
    }

    @Override
    public void deleteAddressById(Long id) throws CustomException {
        Users user = userService.getCurrentLoggedInUser();
        Address address = addressRepository.findByIdAndUsers(id, user).orElseThrow(()-> new NoSuchElementException("Không tồn tại địa chỉ này"));
        if(address.getUsers().getId().equals(user.getId())) {
            addressRepository.delete(address);
            throw new SuccessException("Đã xóa thành công địa chỉ");
        }
        else {
            throw new CustomException("Không tồn tại địa chỉ của bạn", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Address getDefaultAddressForUser(Users user) {
        return addressRepository.findDefaultAddressByUser(user)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy địa chỉ mặc định cho người dùng"));
    }

    @Override
    public Address findByIdAndUser(Long id, Users user) {
        return addressRepository.findByIdAndUsers(id, user).orElseThrow(()-> new NoSuchElementException("Không có địa chỉ của người dùng"));
    }
}
