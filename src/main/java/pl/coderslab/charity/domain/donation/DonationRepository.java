package pl.coderslab.charity.domain.donation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.charity.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("select sum(d.quantity) from Donation d")
    Optional<Long> allQuantityBag();

    @Query("select count(d) from Donation d")
    Optional<Long> allDonations();

    @Query("select count(d) from Donation d where d.user.id = ?1")
    Optional<Long> countDonationsUser(long id);

    @Query("select d from Donation d where d.user = ?1")
    List<Donation> allDonationsUser(User user);

    @Query("select d from Donation d where d.user = ?1 AND d.id = ?2")
    Optional<Donation> donationUserUpdate(User user, long id);
}
