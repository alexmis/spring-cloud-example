package org.mial.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.mial.exception.UserNotFoundException;
import org.mial.model.User;
import org.mial.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final AtomicLong generatorUserId = new AtomicLong();

    private static List<User> users = new ArrayList<>();

    @Override
    public Stream<User> findUsers() {
        return users.stream();
    }

    @Override
    public Page<User> findUsers(Pageable page) {
        if (page.isUnpaged()) {
            return new PageImpl<>(findUsers().collect(toList()));
        } else {
            Stream<User> users = findUsers();

            Iterator<Sort.Order> orders = page.getSort().iterator();
            if (orders.hasNext()) {
                Comparator<User> comparator = getComparing(orders.next());

                while (orders.hasNext()) {
                    comparator = comparator.thenComparing(getComparing(orders.next()));
                }

                users = users.sorted(comparator);
            }

            List<User> collect = users
                .skip(page.getOffset())
                .limit(page.getPageSize())
                .collect(toList());

            return PageableExecutionUtils.getPage(collect, page, () -> findUsers().count());
        }
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return users.stream().filter(e -> Objects.equals(e.getId(), id)).findFirst();
    }

    @Override
    public Long saveUser(User user) {
        if (nonNull(user.getId())) {
            User target = findUserById(user.getId()).orElseThrow(() -> new UserNotFoundException(user.getId()));
            BeanUtils.copyProperties(user, target, "id", "feeds");
            return target.getId();
        } else {
            user.setId(generatorUserId.incrementAndGet());
            users.add(user);
            return user.getId();
        }
    }

    @Override
    public void removeUser(Long id) {
        findUserById(id).ifPresent(account -> users.remove(account));
    }

    @SuppressWarnings("unchecked")
    private <E> Comparator<E> getComparing(Sort.Order order) {
        Comparator<E> comparing = Comparator.comparing(a -> {
            try {
                return (Comparable) PropertyUtils.getProperty(a, order.getProperty());
            } catch (Exception e) {
                return 0;
            }
        }, Comparable::compareTo);

        if (order.getDirection().isAscending()) {
            return comparing;
        }

        return comparing.reversed();
    }
}
