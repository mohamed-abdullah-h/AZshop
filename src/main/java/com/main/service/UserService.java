package com.main.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.main.entity.Address;
import com.main.entity.AppUser;
import com.main.repo.UserRepo;
import com.main.security.AppUserDetail;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepo repo;
	@Autowired
	private AddressService addrService;

	public AppUser findById(long id) {
		return repo.findById(id).get();
	}

	public List<AppUser> findAll() {
		return repo.findAll();
	}

	public AppUser findByEmail(String email) {
		return repo.findByEmail(email);
	}

	public void deleteById(long id) {
		repo.deleteById(id);
	}

	@Transactional
	public AppUser insert(AppUser p) {
		Address addr = p.getAddress();
		if (addr != null) {
			addr = addrService.insert(addr);
		}
		p.setAddress(addr);
		return repo.save(p);
	}

	@Transactional
	public AppUser update(AppUser p) {
		System.err.println(p);
		System.err.println(p.getAddress());
		AppUser oldUser = findById(p.getId());
		oldUser = updateUserFields(oldUser, p);
		Address addr = p.getAddress();
		if(addr == null) return repo.save(p);
		if (addr.getId() == null) {
			addr = addrService.insert(addr);
			oldUser.setAddress(addr);
		} else {
			addr = addrService.update(addr);
		}
		 return repo.save(oldUser);
	}

	public AppUser updateSecInfo(AppUser user) {
		 return repo.save(user);
	}
	private AppUser updateUserFields(AppUser oldUser, AppUser newUser) {
		if(newUser.getName() != null && newUser.getName().trim().length() > 0){
			oldUser.setName(newUser.getName());
		}
			oldUser.setAddress(newUser.getAddress());
		return oldUser;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		AppUser user = findByEmail(email);
		return new AppUserDetail(user);
	}

}
