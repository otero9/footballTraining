package footballtraining.app.model.user;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import footballtraining.app.model.exceptions.SaveNotAvailableException;

/**
 * The Class UserService.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	 /**
	 * Función que almacena los datos relativos a una cuenta en base de datos.
	 *
	 * @param account Este parámetro representa el objeto Account que se quiere almacenar
	 * @return La cuenta resultante, despues de almacenarla
	 * @exception SaveNotAvailableException excepción saveNotAvailable
	 */
	@Transactional
	public footballtraining.app.model.user.User save(footballtraining.app.model.user.User user) throws SaveNotAvailableException {
		if(userRepository.findOneByEmail(user.getEmail()) != null){
			throw new SaveNotAvailableException("Ya existe una cuenta con ese email");
		}
		if(userRepository.findOneByUserName(user.getUserName()) != null) {
			throw new SaveNotAvailableException("Ya existe una cuenta con ese nombre de usuario");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return user;
	}

	@Transactional
	public footballtraining.app.model.user.User find(footballtraining.app.model.user.User user) throws SaveNotAvailableException {

		return userRepository.findOneByEmail(user.getEmail());
	}
	
	/**
	 * Función que actualiza los datos relativos a una cuenta en base de datos.
	 *
	 * @param account Este parámetro representa el nuevo objeto Account que se quiere almacenar
	 * @return La cuenta resultante, despues de actualizarla
	 * @exception SaveNotAvailableException excepción saveNotAvailable
	 */
	@Transactional
	public footballtraining.app.model.user.User update(footballtraining.app.model.user.User user) throws SaveNotAvailableException {
		if((!user.getEmail().equals(userRepository.findOneByEmail(user.getEmail()).getEmail())) && (userRepository.findOneByEmail(user.getEmail()) != null))
			throw new SaveNotAvailableException("Ya existe una cuenta con ese email");
		if((!user.getUserName().equals(userRepository.findOneByUserName(user.getUserName()).getUserName())) && (userRepository.findOneByUserName(user.getUserName()) != null))
			throw new SaveNotAvailableException("Ya existe una cuenta con ese nombre de usuario");	
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return user;
	}
	
	/**
     * Funcion que carga un usuario a partir de su email
     * @param username Este parámetro representa el email de un usuario
     * @return El usuario creado si existe una cuenta asociada a ese email
     * @exception UsernameNotFoundException excepción de nombre de usuario no encontrado
     */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		footballtraining.app.model.user.User user = userRepository.findOneByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return (UserDetails) createUser(user);
	}
	
	/**
	 * Procedimiento que loggea a un usuario para introducir al usuario autenticado en el contexto de la aplicación.
	 *
	 * @param account Este parámetro representa el objeto Account que se quiere loggear
	 */
	public void signin(footballtraining.app.model.user.User user) {
		SecurityContextHolder.getContext().setAuthentication(authenticate(user));
	}
	
	/**
     * Procedimiento que autentica a un usuario
     * @param account Este parámetro representa el objeto Account que se quiere autenticar
     * @return Usuario autenticado
     */
	public Authentication authenticate(footballtraining.app.model.user.User user) {
		return new UsernamePasswordAuthenticationToken(createUser(user), null, Collections.singleton(createAuthority(user)));		
	}
	/**
     * Funcion que crea un usuario
     * @param account Este parámetro representa el objeto User que contiene los datos necesarios para crear un usuario
     * @return El usuario creado
     */
	public User createUser(footballtraining.app.model.user.User user) {
		return new User(user.getUserName(), user.getPassword(), Collections.singleton(createAuthority(user)));
	}
	
	/**
     * Funcion que asigna un rol un usuario
     * @param account Este parámetro representa el objeto User
     * @return El usuario con el nuevo rol
     */
	public GrantedAuthority createAuthority(footballtraining.app.model.user.User user) {
		return new SimpleGrantedAuthority("ROLE_USER");
	}
	
	@Transactional(readOnly=true)
	public footballtraining.app.model.user.User findByUserName(String userName) {
		return userRepository.findOneByUserName(userName);
	}

	public void deleteByUser(footballtraining.app.model.user.User user) {
		userRepository.delete(user);
		
	}

	public void deleteByUserId(Long userId) {
		userRepository.delete(userId);
		
	}

	public footballtraining.app.model.user.User findByUserId(Long userId) {
		return userRepository.getOne(userId);
	}
	
}
