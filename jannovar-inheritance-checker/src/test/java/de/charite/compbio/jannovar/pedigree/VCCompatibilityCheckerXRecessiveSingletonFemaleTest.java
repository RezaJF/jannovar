package de.charite.compbio.jannovar.pedigree;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

/**
 * <p>VCCompatibilityCheckerXRecessiveSingletonFemaleTest class.</p>
 *
 * @author <a href="mailto:max.schubach@charite.de">Max Schubach</a>
 * @since 0.15
 */
public class VCCompatibilityCheckerXRecessiveSingletonFemaleTest extends AbstractCompatibilityCheckerTest {

	/**
	 * <p>setUp.</p>
	 *
	 * @throws de.charite.compbio.jannovar.pedigree.PedParseException if any.
	 */
	@Before
	public void setUp() throws PedParseException {
		ImmutableList.Builder<PedPerson> individuals = new ImmutableList.Builder<PedPerson>();
		individuals.add(new PedPerson("ped", "I.1", "0", "0", Sex.FEMALE, Disease.AFFECTED));
		PedFileContents pedFileContents = new PedFileContents(new ImmutableList.Builder<String>().build(),
				individuals.build());
		this.pedigree = new Pedigree(pedFileContents, "ped");

		this.names = ImmutableList.of("I.1");
	}

	/**
	 * <p>testSizeOfPedigree.</p>
	 */
	@Test
	public void testSizeOfPedigree() {
		Assert.assertEquals(1, pedigree.getMembers().size());
	}

	/**
	 * <p>testCaseNegativesOneVariant.</p>
	 */
	@Test
	public void testCaseNegativesOneVariant() {
		Assert.assertFalse(!buildCheckerXR(REF).run().isEmpty());
		Assert.assertFalse(!buildCheckerXR(UKN).run().isEmpty());
		Assert.assertFalse(!buildCheckerXR(HET).run().isEmpty());
	}

	/**
	 * <p>testCaseNegativesTwoVariants.</p>
	 */
	@Test
	public void testCaseNegativesTwoVariants() {
		Assert.assertFalse(!buildCheckerXR(REF, REF).run().isEmpty());
		Assert.assertFalse(!buildCheckerXR(REF, UKN).run().isEmpty());
		Assert.assertFalse(!buildCheckerXR(UKN, UKN).run().isEmpty());
		Assert.assertFalse(!buildCheckerXR(HET, UKN).run().isEmpty());
	}

	/**
	 * <p>testCasePositiveOneVariant.</p>
	 */
	@Test
	public void testCasePositiveOneVariant() {
		Assert.assertTrue(buildCheckerXR(ALT).run().size() == 1);
	}

	/**
	 * <p>testCasePositiveTwoVariants.</p>
	 */
	@Test
	public void testCasePositiveTwoVariants() {
		Assert.assertTrue(buildCheckerXR(HET, HET).run().size() == 2);
		Assert.assertTrue(buildCheckerXR(ALT, REF).run().size() == 1);
		Assert.assertTrue(buildCheckerXR(UKN, ALT).run().size() == 1);
		Assert.assertTrue(buildCheckerXR(ALT, ALT).run().size() == 2);
	}

}